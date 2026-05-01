# My Profile App - Tugas Praktikum PAM Week 4

Aplikasi profil diri yang dikembangkan dengan pola arsitektur **MVVM** dan fitur kustomisasi UI.

## 📝 Deskripsi Tugas (Minggu 4)
Mengembangkan Profile App dari minggu lalu dengan fitur:
1. **Implementasi MVVM Pattern**:
   - Menggunakan `ProfileViewModel` dengan `StateFlow` untuk manajemen state.
   - Data state didefinisikan dalam data class `ProfileUiState`.
2. **Fitur Edit Profile**:
   - Form untuk mengedit nama dan bio secara langsung.
   - Implementasi **State Hoisting** untuk komponen `TextField`.
   - Tombol "Save Changes" untuk memperbarui data di ViewModel.
3. **Fitur Dark Mode Toggle**:
   - Switch untuk berpindah antara Dark/Light mode.
   - State tema disimpan dan dikelola di dalam ViewModel.

## 🚀 Fitur & Implementasi
- **MVVM Architecture**: Pemisahan logika bisnis (ViewModel) dari UI (Compose).
- **Reactive UI**: UI otomatis diperbarui ketika `StateFlow` di ViewModel berubah.
- **Dynamic Theme**: Dukungan penuh untuk tema Gelap/Terang yang dikontrol oleh pengguna.
- **State Hoisting**: Input pengguna dikelola secara terpusat untuk menjaga konsistensi data.

## 🛠️ Struktur Folder
- `data/`: Berisi `ProfileUiState.kt`.
- `viewmodel/`: Berisi `ProfileViewModel.kt`.
- `ui/`: Berisi komponen antarmuka pengguna.
- `MainActivity.kt`: Entry point aplikasi dan root composable.

## 📸 Screenshots
<img width="446" height="954" alt="profile" src="https://github.com/user-attachments/assets/ff98f0e2-c706-49f9-b59c-ac42ee9712a9" />
<img width="440" height="948" alt="darkmode" src="https://github.com/user-attachments/assets/ad010749-ca4a-420a-b726-1ec562c7ea99" />
<img width="433" height="941" alt="editprofile" src="https://github.com/user-attachments/assets/1ca553d7-e042-43cf-840b-4cf02714e5ca" />



## 👤 Identitas
- **Nama**: Andre Prasetya Daely
- **NIM**: 123140131
- **Prodi**: Teknik Informatika ITERA
- **Branch**: `week-4`
