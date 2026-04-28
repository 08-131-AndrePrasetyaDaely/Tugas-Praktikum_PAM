# News Feed Simulator

Proyek simulasi pembaca berita menggunakan Kotlin Coroutines dan Flow.

## Fitur Utama

1.  **News Flow Simulator**: Menggunakan `flow` builder untuk memancarkan data berita baru secara otomatis setiap 2 detik.
2.  **Filter Kategori**: Menggunakan operator `.filter` untuk menyaring berita berdasarkan kategori yang dipilih (All, Tech, Sports, dll).
3.  **Data Transformation**: Menggunakan operator `.map` untuk mengubah data mentah (`NewsItem`) menjadi model tampilan (`NewsDisplayModel`) dengan format waktu yang ramah pengguna.
4.  **Read Tracker (StateFlow)**: Menggunakan `StateFlow` untuk menyimpan dan melacak jumlah berita yang telah diklik/dibaca oleh pengguna secara reaktif.
5.  **Async Detail Fetching**: Menggunakan `async`/`await` di dalam Coroutine untuk mensimulasikan pengambilan detail berita dari sumber eksternal secara asynchronous.
6.  **Error Handling**: Implementasi operator `.catch` pada Flow untuk menangani potensi kesalahan selama aliran data berlangsung.

## Cara Menjalankan Proyek

1.  Buka proyek ini di **Android Studio**.
2.  Pastikan koneksi internet tersedia untuk sinkronisasi Gradle.
3.  Klik tombol **Run** (Ikon Play hijau) untuk menjalankan aplikasi pada emulator atau perangkat fisik Android.
4.  Pilih filter kategori untuk melihat penyaringan berita secara real-time.
5.  Klik tombol **"Baca Selengkapnya"** untuk meningkatkan counter berita yang dibaca dan memicu pengambilan detail secara async.

## Teknologi yang Digunakan

-   **Kotlin Coroutines**: Untuk operasi asynchronous.
-   **Kotlin Flow**: Untuk menangani aliran data stream secara reaktif.
-   **StateFlow**: Untuk manajemen state UI.
-   **Jetpack Compose**: Untuk membangun antarmuka pengguna yang deklaratif.
-   **ViewModel**: Sebagai pemisah logika bisnis dan UI.
