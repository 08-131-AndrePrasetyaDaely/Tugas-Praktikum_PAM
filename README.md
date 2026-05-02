# News Reader App - Tugas Praktikum Minggu 6

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
*(Silakan lampirkan screenshot aplikasi Anda di bawah ini)*

| Loading State | Success State | Detail Screen |
|---|---|---|
| ![Loading](https://via.placeholder.com/200x400?text=Loading+State) | ![Success](https://via.placeholder.com/200x400?text=Success+State) | ![Detail](https://via.placeholder.com/200x400?text=Detail+Screen) |

## 🚀 Cara Menjalankan
1. Clone repository ini.
2. Buka di Android Studio (Koala atau versi terbaru).
3. Jalankan **Gradle Sync**.
4. Run aplikasi di Emulator atau Perangkat Fisik.



