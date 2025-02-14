# ESHOP
## Module 1
### Reflection 1

Berikut adalah beberapa poin mengenai standar penulisan kode (clean code) dan praktik keamanan (secure coding) yang telah saya terapkan, serta perbaikan yang dapat dilakukan:

1. **Struktur Kode yang Rapi**  
   - Sudah menggunakan package terpisah untuk controller, service, repository, dan model, sehingga tanggung jawab setiap komponen jelas.  
   - Penggunaan anotasi Spring Boot (@Controller, @Service, @Repository) membantu memisahkan layer bisnis, presentasi, dan akses data.

2. **Konvensi Penamaan**  
   - Kelas, metode, dan variabel menggunakan penamaan yang sesuai dengan praktik Java, misalnya:  
     - Kelas dengan huruf kapital di awal (CreateProductFunctionalTest, ProductRepository).  
     - Variabel dengan camelCase (productRepository, productName).

3. **Validasi Input (Perbaikan Disarankan)**  
   - Pada saat Create/Edit Product, belum ada validasi data input, misalnya kuantitas negatif atau nama kosong.  
   - Diperlukan penanganan error yang lebih baik di sisi server (misalnya menggunakan @Valid atau manual checking) untuk mencegah data tidak valid masuk ke sistem.

4. **Keamanan (Secure Coding)**  
   - Penggunaan thymeleaf sudah dibantu dengan mekanisme penanganan data (th:field) untuk meminimalkan risiko XSS (meski tetap perlu perhatian ekstra jika input user tidak disaring).  
   - Metode HTTP yang semestinya (POST, PUT, DELETE) sudah didukung, tetapi bisa lebih ditingkatkan dengan token CSRF (Cross-Site Request Forgery) jika fitur autentikasi/otorisasi sudah diterapkan.

5. **Pengujian yang Baik**  
   - Sudah ada pemisahan antara unit test dan functional test (misalnya, CreateProductFunctionalTest untuk pengujian fungsional).  
   - Tes memastikan fitur utama (Create, Edit, Delete) berjalan sesuai harapan, termasuk skenario negatif.

6. **Perbaikan Potensial**  
   - Penambahan validasi input pada sisi server dan form.  
   - Pertimbangkan penanganan kesalahan yang lebih komprehensif (misalnya, mengarahkan user ke halaman error jika operasi gagal).  
   - Pastikan log level sesuai (info/debug/warn/error) pada operasi penting untuk memudahkan debugging dan pemantauan di production.

### Reflection 2

Setelah menulis unit test baru, saya mempelajari beberapa hal berikut:

1. **Perasaan Setelah Menulis Unit Test**  
   - Menulis unit test dapat memberikan rasa percaya diri bahwa kode kita bekerja sebagaimana mestinya. Namun, sering kali kita juga menemukan bahwa semakin kita menguji, semakin kita menyadari potensi kasus tepi (edge cases) yang belum kita tangani.

2. **Jumlah Unit Test dalam Satu Kelas**  
   - Tidak ada aturan baku tentang berapa banyak tes yang perlu dibuat pada satu kelas, namun sebaiknya kita membuat tes untuk setiap skenario penting dan edge cases yang mungkin terjadi. Sebagai contoh, jika kita memiliki beberapa metode penting, kita dapat membuat setidaknya satu atau lebih tes yang memvalidasi perilaku masing-masing metode tersebut.

3. **Kecukupan Unit Test**  
   - Code coverage dapat membantu kita memahami berapa banyak baris kode atau cabang logika yang teruji. Namun, meskipun code coverage mencapai 100%, bukan berarti kode bebas dari bug. Terkadang, ada bug yang hanya muncul pada skenario tertentu yang tidak tercover oleh unit test.

4. **Membuat Functional Test Baru**  
   - Ketika diminta membuat functional test baru untuk memverifikasi jumlah item dalam product list, kita mungkin akan membuat kelas Java serupa dengan *setup* dan *instance variable* yang sama. Hal ini bisa menimbulkan beberapa permasalahan terkait **clean code**:
     - **Duplikasi Kode**: Potensi terjadinya pengulangan (misalnya, konfigurasi Selenium, inisialisasi driver, base URL) yang sudah ada di functional test lain.  
     - **Struktur yang Berulang**: Jika kita terus menyalin kode yang sama dari satu kelas ke kelas lain, hal ini akan menyulitkan pemeliharaan di masa depan.

5. **Dampak terhadap Kualitas Kode**  
   - Kode duplikat membuat perawatan lebih sulit. Jika ada perubahan pada konfigurasi test (misalnya, cara kita mengatur base URL atau driver), kita harus memperbarui di banyak tempat. Ini dapat mengurangi *readability* dan meningkatkan risiko kesalahan.

6. **Saran Peningkatan**  
   - **Abstraksi & Refactoring**: Membuat kelas atau metode helper yang bisa digunakan bersama untuk inisialisasi driver, konfigurasi umum, atau pembersihan (teardown).  
   - **Gunakan *Inheritance* atau *Composition***: Kita dapat membuat kelas dasar (misalnya `BaseFunctionalTest`) yang menangani setup rutin, lalu kelas fungsional lain mewarisi atau memakai fungsi dari kelas tersebut.  
   - **Penamaan yang Konsisten**: Pastikan metode dan variabel di functional test memakai nama yang jelas dan konsisten, agar mudah dibedakan dengan unit test.