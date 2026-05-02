# News Reader App - Praktikum Minggu 6

Aplikasi Android "News Reader" yang dibangun menggunakan Jetpack Compose untuk menampilkan berita terkini. Proyek ini mengimplementasikan pengambilan data dari API, arsitektur Repository Pattern, dan manajemen UI State yang baik sesuai dengan kriteria penilaian.

## 📋 Fitur Utama
- **Fetch Berita**: Mengambil data artikel berita (Title, Description, Image).
- **Detail Screen**: Navigasi ke halaman detail saat artikel diklik untuk membaca konten lengkap.
- **Pull to Refresh**: Menarik daftar ke bawah untuk memperbarui berita.
- **UI States**: Penanganan kondisi **Loading**, **Success**, dan **Error** yang informatif.
- **Repository Pattern**: Pemisahan logika pengambilan data (Data Source) dengan tampilan (UI).

## 🛠️ Tech Stack
- **UI**: Jetpack Compose (Material 3)
- **Networking**: Ktor Client (Android Engine)
- **Serialization**: Kotlinx Serialization (JSON Parsing)
- **Image Loading**: Coil Compose
- **Navigation**: Navigation Compose
- **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern

## 🏗️ Struktur Proyek
- `data/model`: Data classes untuk parsing JSON (`Article`, `NewsResponse`).
- `data/remote`: Service untuk pemanggilan API menggunakan Ktor.
- `data/repository`: Implementasi Repository Pattern untuk abstraksi data.
- `ui/viewmodel`: Manajemen state UI dan logika bisnis.
- `ui/screen`: Komponen UI (Daftar Berita dan Detail Berita).

## 🌐 Informasi API
- **API yang digunakan**: [NewsAPI.org](https://newsapi.org/)
- **Endpoint**: `/v2/top-headlines`
- **Konfigurasi**: Implementasi saat ini menggunakan Mock Data berita bola (Bayern Munich vs Heidenheim) untuk memastikan tampilan visual yang stabil dan relevan sesuai permintaan spesifikasi.

## 📸 Screenshots


| Loading State | Success State | Detail Screen |
| :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/e38c9bab-2a76-4207-b7b5-956fc4649950" width="32%" /> | <img src="https://github.com/user-attachments/assets/f3016b67-2175-498d-8eb7-353545384d69" width="32%" /> | <img src="https://github.com/user-attachments/assets/391d315e-f156-4720-92f2-c89b8dc39c2c" width="32%" /> |

## 🚀 Cara Menjalankan
1. Clone repository ini.
2. Buka di Android Studio (Koala atau versi terbaru).
3. Jalankan **Gradle Sync**.
4. Run aplikasi di Emulator atau Perangkat Fisik.



