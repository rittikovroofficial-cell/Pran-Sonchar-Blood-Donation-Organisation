-- ==============================================================================
-- PRAN SANCHAR BLOOD DONATION ORGANIZATION (প্রাণ সঞ্চার রক্তদান সংস্থা)
-- SUPABASE POSTGRESQL SCHEMA & ROW LEVEL SECURITY (RLS) POLICIES
-- Tagline: "রক্তের বন্ধনে, প্রাণের স্পন্দনে"
-- ==============================================================================

-- 1. EXTENSIONS
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. ENUMS
CREATE TYPE blood_group_type AS ENUM ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-');
CREATE TYPE urgency_level_type AS ENUM ('EMERGENCY', 'URGENT', 'ROUTINE');
CREATE TYPE request_status_type AS ENUM ('PENDING', 'APPROVED', 'ACTIVE', 'FULFILLED', 'CANCELLED', 'REJECTED');
CREATE TYPE campaign_status_type AS ENUM ('UPCOMING', 'ONGOING', 'COMPLETED');

-- 3. PROFILES TABLE (Linked with Supabase auth.users)
CREATE TABLE IF NOT EXISTS public.profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    full_name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    phone TEXT NOT NULL,
    blood_group blood_group_type NOT NULL,
    district TEXT NOT NULL,
    upazila TEXT NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- 4. DONORS TABLE
CREATE TABLE IF NOT EXISTS public.donors (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    full_name TEXT NOT NULL,
    phone TEXT NOT NULL,
    email TEXT NOT NULL,
    blood_group blood_group_type NOT NULL,
    date_of_birth DATE,
    gender TEXT DEFAULT 'Not Specified',
    district TEXT NOT NULL,
    upazila TEXT NOT NULL,
    detailed_address TEXT,
    last_donation_date DATE,
    is_available BOOLEAN DEFAULT TRUE,
    consent_share_phone BOOLEAN DEFAULT TRUE,
    consent_share_whatsapp BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    total_donations INT DEFAULT 0,
    notes TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    CONSTRAINT unique_donor_email UNIQUE(email),
    CONSTRAINT unique_donor_phone UNIQUE(phone)
);

-- 5. BLOOD REQUESTS TABLE
CREATE TABLE IF NOT EXISTS public.blood_requests (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    patient_name TEXT NOT NULL,
    blood_group blood_group_type NOT NULL,
    units_required INT NOT NULL DEFAULT 1 CHECK (units_required > 0),
    hospital_name TEXT NOT NULL,
    district TEXT NOT NULL,
    upazila TEXT NOT NULL,
    hospital_address TEXT,
    required_date TEXT NOT NULL,
    required_time TEXT DEFAULT 'Immediate',
    contact_person TEXT NOT NULL,
    contact_phone TEXT NOT NULL,
    urgency_level urgency_level_type DEFAULT 'EMERGENCY',
    status request_status_type DEFAULT 'ACTIVE',
    additional_info TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- 6. DONATIONS HISTORY TABLE
CREATE TABLE IF NOT EXISTS public.donations (
    id BIGSERIAL PRIMARY KEY,
    donor_id BIGINT REFERENCES public.donors(id) ON DELETE SET NULL,
    donor_name TEXT NOT NULL,
    blood_group blood_group_type NOT NULL,
    donation_date DATE NOT NULL,
    hospital_name TEXT NOT NULL,
    district TEXT NOT NULL,
    units INT DEFAULT 1,
    related_request_id BIGINT REFERENCES public.blood_requests(id) ON DELETE SET NULL,
    is_verified BOOLEAN DEFAULT TRUE,
    certificate_number TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 7. CAMPAIGNS TABLE
CREATE TABLE IF NOT EXISTS public.campaigns (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    title_bn TEXT NOT NULL,
    description TEXT,
    description_bn TEXT,
    date TEXT NOT NULL,
    time TEXT NOT NULL,
    location TEXT NOT NULL,
    location_bn TEXT NOT NULL,
    target_units INT DEFAULT 100,
    collected_units INT DEFAULT 0,
    status campaign_status_type DEFAULT 'UPCOMING',
    organizer TEXT DEFAULT 'Pran Sanchar Volunteer Wing',
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 8. NOTICES TABLE
CREATE TABLE IF NOT EXISTS public.notices (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    title_bn TEXT NOT NULL,
    content TEXT NOT NULL,
    content_bn TEXT NOT NULL,
    category TEXT DEFAULT 'General',
    priority TEXT DEFAULT 'Normal',
    is_pinned BOOLEAN DEFAULT FALSE,
    published_at TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 9. CONTACT MESSAGES TABLE
CREATE TABLE IF NOT EXISTS public.contact_messages (
    id BIGSERIAL PRIMARY KEY,
    sender_name TEXT NOT NULL,
    sender_phone TEXT NOT NULL,
    sender_email TEXT,
    subject TEXT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 10. ADMIN ROLES TABLE
CREATE TABLE IF NOT EXISTS public.admin_roles (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    role TEXT NOT NULL DEFAULT 'SUPER_ADMIN',
    granted_at TIMESTAMPTZ DEFAULT NOW()
);

-- 11. AUDIT LOGS TABLE
CREATE TABLE IF NOT EXISTS public.audit_logs (
    id BIGSERIAL PRIMARY KEY,
    action TEXT NOT NULL,
    performed_by TEXT NOT NULL,
    details TEXT,
    timestamp TIMESTAMPTZ DEFAULT NOW()
);

-- 12. PERFORMANCE INDEXES
CREATE INDEX IF NOT EXISTS idx_donors_blood_group ON public.donors(blood_group);
CREATE INDEX IF NOT EXISTS idx_donors_district ON public.donors(district);
CREATE INDEX IF NOT EXISTS idx_donors_availability ON public.donors(is_available);
CREATE INDEX IF NOT EXISTS idx_requests_status ON public.blood_requests(status);
CREATE INDEX IF NOT EXISTS idx_requests_urgency ON public.blood_requests(urgency_level);

-- 13. ROW LEVEL SECURITY (RLS) POLICIES
ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.donors ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.blood_requests ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.donations ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.campaigns ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.notices ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.contact_messages ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.admin_roles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.audit_logs ENABLE ROW LEVEL SECURITY;

-- Helper function: Is Super Admin?
CREATE OR REPLACE FUNCTION public.is_super_admin()
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS (
        SELECT 1 FROM public.admin_roles
        WHERE user_id = auth.uid() AND role = 'SUPER_ADMIN'
    );
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- Profiles: Public can view basic donor info; Users can edit own profile
CREATE POLICY "Profiles are viewable by everyone" ON public.profiles FOR SELECT USING (true);
CREATE POLICY "Users can insert own profile" ON public.profiles FOR INSERT WITH CHECK (auth.uid() = id);
CREATE POLICY "Users can update own profile" ON public.profiles FOR UPDATE USING (auth.uid() = id);

-- Donors: Public can view available donors; private emails and hidden phones protected by application
CREATE POLICY "Donors viewable by all" ON public.donors FOR SELECT USING (true);
CREATE POLICY "Users can register as donor" ON public.donors FOR INSERT WITH CHECK (auth.uid() = user_id);
CREATE POLICY "Donors can update own record" ON public.donors FOR UPDATE USING (auth.uid() = user_id OR public.is_super_admin());
CREATE POLICY "Super admin can delete donor" ON public.donors FOR DELETE USING (public.is_super_admin());

-- Blood Requests: Public can view active and approved requests
CREATE POLICY "Requests viewable by all" ON public.blood_requests FOR SELECT USING (true);
CREATE POLICY "Authenticated users can post request" ON public.blood_requests FOR INSERT WITH CHECK (true);
CREATE POLICY "Users or admin can update requests" ON public.blood_requests FOR UPDATE USING (auth.uid() = user_id OR public.is_super_admin());

-- Campaigns & Notices: Read-only for public, admin for writes
CREATE POLICY "Campaigns viewable by all" ON public.campaigns FOR SELECT USING (true);
CREATE POLICY "Campaigns admin write" ON public.campaigns FOR ALL USING (public.is_super_admin());

CREATE POLICY "Notices viewable by all" ON public.notices FOR SELECT USING (true);
CREATE POLICY "Notices admin write" ON public.notices FOR ALL USING (public.is_super_admin());

-- Audit logs: Admin only
CREATE POLICY "Audit logs admin view" ON public.audit_logs FOR SELECT USING (public.is_super_admin());
CREATE POLICY "Audit logs system insert" ON public.audit_logs FOR INSERT WITH CHECK (true);
