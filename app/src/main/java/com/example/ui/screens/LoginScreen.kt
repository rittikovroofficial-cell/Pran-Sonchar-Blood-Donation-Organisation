package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedDark
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.LightBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun LoginScreen(
  viewModel: MainViewModel,
  onLoginSuccess: () -> Unit,
  onNavigateToRegister: () -> Unit
) {
  val language by viewModel.language.collectAsState()

  var email by remember { mutableStateOf("tanvir.hasan@example.bd") }
  var password by remember { mutableStateOf("123456") }
  var passwordVisible by remember { mutableStateOf(false) }
  var rememberMe by remember { mutableStateOf(true) }

  var showForgotPasswordDialog by remember { mutableStateOf(false) }
  var resetEmailInput by remember { mutableStateOf("") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .testTag("login_screen_column"),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top Hero Branding
    item {
      Spacer(modifier = Modifier.height(10.dp))

      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(Color.White)
          .border(2.dp, BloodRedPrimary, CircleShape)
          .padding(6.dp),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.ic_pran_sanchar),
          contentDescription = "Pran Sanchar Logo",
          modifier = Modifier
            .size(68.dp)
            .clip(CircleShape)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = AppStrings.orgName(language),
        fontWeight = FontWeight.ExtraBold,
        fontSize = if (language == AppLanguage.BENGALI) 20.sp else 18.sp,
        color = DarkNavy
      )

      Text(
        text = "“${AppStrings.orgTagline(language)}”",
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = BloodRedPrimary
      )

      Spacer(modifier = Modifier.height(24.dp))
    }

    // Login Form Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("login_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column(modifier = Modifier.padding(22.dp)) {
          Text(
            text = AppStrings.loginTitle(language),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = DarkNavy
          )

          Text(
            text = if (language == AppLanguage.BENGALI)
              "রক্তদাতা প্রোফাইল ও রক্ত আবেদনের তথ্য পরিচালনা করতে লগইন করুন।"
            else
              "Sign in to manage your donor profile, requests & blood drives.",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
          )

          // Email Input
          Text(
            text = AppStrings.emailLabel(language),
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = DarkNavy
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("example@pransanchar.org", fontSize = 13.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = BloodRedPrimary
              )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("login_email_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = BloodRedPrimary,
              focusedLabelColor = BloodRedPrimary
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Password Input
          Text(
            text = AppStrings.passwordLabel(language),
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = DarkNavy
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("••••••••", fontSize = 13.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = BloodRedPrimary
              )
            },
            trailingIcon = {
              IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                  imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                  contentDescription = "Toggle password visibility",
                  tint = TextSecondary
                )
              }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("login_password_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = BloodRedPrimary,
              focusedLabelColor = BloodRedPrimary
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Remember Me & Forgot Password Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(checkedColor = BloodRedPrimary)
              )
              Text(
                text = AppStrings.rememberMe(language),
                fontSize = 12.sp,
                color = TextPrimary
              )
            }

            Text(
              text = AppStrings.forgotPassword(language),
              fontSize = 12.sp,
              color = BloodRedPrimary,
              fontWeight = FontWeight.Bold,
              modifier = Modifier
                .clickable {
                  resetEmailInput = email
                  showForgotPasswordDialog = true
                }
                .testTag("forgot_password_button")
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Sign In Button
          Button(
            onClick = {
              viewModel.login(email.trim(), password) {
                onLoginSuccess()
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("login_submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = AppStrings.signInBtn(language),
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = Color.White
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Demo Autofill Pill
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(BloodRedContainer)
              .clickable {
                email = "tanvir.hasan@example.bd"
                password = "donor_demo_pass"
                viewModel.showToast(
                  if (language == AppLanguage.BENGALI)
                    "ডেমো রক্তদাতা তথ্য পূরণ করা হয়েছে"
                  else
                    "Autofilled with demo donor account"
                )
              }
              .padding(vertical = 8.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (language == AppLanguage.BENGALI)
                "⚡ ডেমো একাউন্ট ব্যবহার করুন: tanvir.hasan@example.bd"
              else
                "⚡ Quick Fill Demo: tanvir.hasan@example.bd",
              fontSize = 11.sp,
              color = BloodRedDark,
              fontWeight = FontWeight.SemiBold
            )
          }

          Spacer(modifier = Modifier.height(20.dp))

          HorizontalDivider(color = CardBorder)

          Spacer(modifier = Modifier.height(16.dp))

          // Register Action
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = AppStrings.noAccountText(language),
              fontSize = 13.sp,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = AppStrings.becomeDonorBtn(language),
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = DarkNavy,
              modifier = Modifier
                .clickable { onNavigateToRegister() }
                .testTag("login_goto_register_button")
            )
          }
        }
      }
    }
  }

  // Forgot Password Dialog
  if (showForgotPasswordDialog) {
    AlertDialog(
      onDismissRequest = { showForgotPasswordDialog = false },
      title = {
        Text(
          text = if (language == AppLanguage.BENGALI) "পাসওয়ার্ড রিসেট করুন" else "Reset Password",
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Column {
          Text(
            text = if (language == AppLanguage.BENGALI)
              "আপনার নিবন্ধিত ইমেইল ঠিকানা দিন। আমরা পাসওয়ার্ড রিসেট লিংক পাঠিয়ে দেব।"
            else
              "Enter your registered email address. We will send a secure password reset link.",
            fontSize = 12.sp,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(12.dp))
          OutlinedTextField(
            value = resetEmailInput,
            onValueChange = { resetEmailInput = it },
            label = { Text(AppStrings.emailLabel(language)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.showToast(
              if (language == AppLanguage.BENGALI)
                "পাসওয়ার্ড রিসেট নির্দেশিকা পাঠানো হয়েছে: $resetEmailInput"
              else
                "Password reset instructions sent to: $resetEmailInput"
            )
            showForgotPasswordDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary)
        ) {
          Text(if (language == AppLanguage.BENGALI) "পাঠান" else "Send Link")
        }
      },
      dismissButton = {
        TextButton(onClick = { showForgotPasswordDialog = false }) {
          Text(if (language == AppLanguage.BENGALI) "বাতিল" else "Cancel")
        }
      }
    )
  }
}
