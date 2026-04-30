# My Profile App

A simple Android application built with Jetpack Compose to display a user profile.

## Features
- **Profile Header**: Displays a circular profile picture and the user's name.
- **Bio Section**: A short description of the user.
- **Contact Information**: A card containing Email, Phone, and Location.
- **Interactive**: A "Show Details" button that toggles the visibility of the contact information with a fade animation.

## Components Used
- `Column`, `Row`, `Box` (Layouts)
- `Card` (Material Design 3)
- `Text`, `Button`, `Image`, `Icon` (UI Elements)
- `Modifier` (Styling: padding, clip, border, fillMaxSize, etc.)
- `AnimatedVisibility` (Animation bonus)

## Reusable Composables
1. `ProfileHeader`: Renders the top section of the profile.
2. `ProfileCard`: Renders the detailed information card.
3. `InfoItem`: A reusable row for displaying a specific piece of information with an icon.

## Screenshot
![Profile Screenshot](screenshot.png)
*(Note: Please replace this with the actual screenshot of the running app)*

## How to Build
1. Open the project in Android Studio.
2. Sync Gradle.
3. Run the `app` module on an emulator or physical device.
