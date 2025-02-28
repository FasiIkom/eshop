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

## Module 2
### Reflection 4.2

1. **Code Quality Issues yang Diperbaiki**
   - **Duplikasi Kode dalam Unit Test**: Saya mengeliminasi kode yang berulang dengan membuat metode helper yang dapat digunakan kembali.
   - **Kurangnya Validasi Input**: Saya menambahkan validasi input pada endpoint API untuk memastikan data yang masuk sudah sesuai dengan aturan bisnis.
   - **Peringatan dari Static Code Analysis**: Saya memperbaiki peringatan dari alat analisis kode statis (misalnya SonarQube) seperti penggunaan variabel yang tidak digunakan dan kompleksitas kode yang tinggi.

2. **Evaluasi CI/CD Workflows**
   - Implementasi saat ini sudah memenuhi definisi **Continuous Integration (CI)** karena setiap perubahan kode yang dikirimkan akan langsung diuji melalui pipeline otomatis yang mencakup unit testing dan code analysis.
   - Untuk **Continuous Deployment (CD)**, sistem ini sudah memungkinkan deployment otomatis ke PaaS setelah pipeline berhasil dijalankan. Namun, ada beberapa perbaikan yang bisa dilakukan, seperti rollback otomatis jika deployment gagal.
   - Secara keseluruhan, pipeline CI/CD ini membantu mengurangi kesalahan manual dalam proses deployment, meningkatkan kecepatan rilis, dan memastikan bahwa hanya kode yang telah diuji yang masuk ke lingkungan produksi.

## Module 3
### Principles Applied to the Project

1. **Single Responsibility Principle (SRP)**
   - Setiap kelas dan metode dalam proyek ini memiliki satu tanggung jawab yang jelas. Misalnya, `CarController` hanya bertanggung jawab untuk menangani permintaan HTTP terkait mobil, sementara `CarService` bertanggung jawab untuk logika bisnis terkait mobil.
   - **Modifikasi yang telah dibuat:** Memisahkan tanggung jawab antara `CarController` dan `ProductController`. `CarController` hanya menangani permintaan HTTP terkait car, sedangkan `ProductController` menangani permintaan HTTP terkait product.

2. **Open/Closed Principle (OCP)**
   - Kelas dan modul dalam proyek ini dirancang agar terbuka untuk ekstensi tetapi tertutup untuk modifikasi. Misalnya, `CarService` adalah sebuah antarmuka yang dapat diimplementasikan oleh berbagai kelas tanpa mengubah antarmuka itu sendiri.
   - **Modifikasi yang telah dibuat:** Menggunakan antarmuka `CarService` yang dapat diimplementasikan oleh berbagai kelas seperti `CarServiceImpl` dan `InMemoryCarService` tanpa mengubah antarmuka itu sendiri.

3. **Liskov Substitution Principle (LSP)**
   - Subkelas dapat menggantikan superclass mereka tanpa mengganggu fungsionalitas program. Misalnya, setiap implementasi `CarService` dapat digunakan di mana saja `CarService` diperlukan tanpa mempengaruhi logika aplikasi.
   - **Modifikasi yang telah dibuat:** Memastikan bahwa setiap implementasi `CarService` dapat digunakan secara interchangeably tanpa mempengaruhi logika aplikasi. Contohnya, `CarServiceImpl` dapat digunakan di mana saja `CarService` diperlukan.

4. **Interface Segregation Principle (ISP)**
   - Antarmuka yang besar dipecah menjadi antarmuka yang lebih kecil dan lebih spesifik. Misalnya, `CarService` hanya memiliki metode yang relevan dengan operasi mobil, sehingga klien tidak dipaksa untuk bergantung pada metode yang tidak mereka gunakan.
   - **Modifikasi yang telah dibuat:** Membuat interface `CarService` yang hanya memiliki metode yang relevan dengan operasi mobil, sehingga klien tidak dipaksa untuk bergantung pada metode yang tidak mereka gunakan.

5. **Dependency Inversion Principle (DIP)**
   - Modul tingkat tinggi tidak bergantung pada modul tingkat rendah, tetapi keduanya bergantung pada abstraksi. Misalnya, `CarController` bergantung pada `CarService` daripada `CarServiceImpl`, memungkinkan injeksi dependensi dan pengujian yang lebih mudah.
   - **Modifikasi yang telah dibuat:** Menggunakan interface `CarService` di `CarController` dan menginjeksi dependensi menggunakan Spring's `@Autowired`, sehingga `CarController` tidak bergantung pada implementasi konkret `CarServiceImpl`.

### Advantages of Applying SOLID Principles

1. **Maintainability**
   - Dengan memisahkan tanggung jawab dan menggunakan antarmuka, kode menjadi lebih mudah untuk dipelihara dan diperbarui. Misalnya, jika ada perubahan pada logika bisnis mobil, kita hanya perlu memperbarui `CarServiceImpl` tanpa mengubah `CarController`.
   - **Modifikasi yang telah dibuat:** Memisahkan tanggung jawab antara `CarController` dan `CarService`, serta menggunakan antarmuka `CarService`.

2. **Testability**
   - Dengan menggunakan antarmuka dan injeksi dependensi, kita dapat dengan mudah membuat mock atau stub untuk pengujian unit. Misalnya, kita dapat menguji `CarController` dengan mock `CarService` tanpa bergantung pada implementasi konkret.
   - **Modifikasi yang telah dibuat:** Menggunakan antarmuka `CarService` dan injeksi dependensi di `CarController`, memungkinkan penggunaan mock `CarService` untuk pengujian unit.

3. **Flexibility and Extensibility**
   - Kode yang mengikuti prinsip OCP dan DIP lebih mudah untuk diperluas tanpa mengubah kode yang ada. Misalnya, kita dapat menambahkan implementasi baru dari `CarService` untuk mendukung penyimpanan data yang berbeda tanpa mengubah `CarController`.
   - **Modifikasi yang telah dibuat:** Menggunakan antarmuka `CarService` yang dapat diimplementasikan oleh berbagai kelas, memungkinkan penambahan implementasi baru tanpa mengubah kode yang ada.

4. **Readability and Understandability**
   - Kode yang mengikuti prinsip SRP dan ISP lebih mudah dibaca dan dipahami karena setiap kelas dan antarmuka memiliki tanggung jawab yang jelas dan spesifik.
   - **Modifikasi yang telah dibuat:** Memisahkan tanggung jawab antara `CarController` dan `CarService`, serta membuat antarmuka `CarService` yang spesifik.

### Disadvantages of Not Applying SOLID Principles

1. **Tight Coupling**
   - Tanpa DIP, modul tingkat tinggi akan bergantung pada modul tingkat rendah, membuat kode sulit untuk diuji dan dipelihara. Misalnya, jika `CarController` bergantung langsung pada `CarServiceImpl`, setiap perubahan pada `CarServiceImpl` dapat mempengaruhi `CarController`.
   - **Modifikasi yang telah dibuat:** Menggunakan antarmuka `CarService` di `CarController` dan menginjeksi dependensi menggunakan Spring's `@Autowired`.

2. **Code Duplication**
   - Tanpa SRP dan ISP, kode cenderung memiliki tanggung jawab ganda dan antarmuka besar, yang dapat menyebabkan duplikasi kode dan kesulitan dalam pemeliharaan. Misalnya, jika `CarService` memiliki terlalu banyak metode yang tidak terkait, klien akan dipaksa untuk mengimplementasikan atau menggunakan metode yang tidak mereka butuhkan.
   - **Modifikasi yang telah dibuat:** Memisahkan tanggung jawab antara `CarController` dan `CarService`, serta membuat antarmuka `CarService` yang spesifik.

3. **Difficulty in Extending Functionality**
   - Tanpa OCP, setiap perubahan atau penambahan fitur baru akan memerlukan modifikasi pada kode yang ada, meningkatkan risiko bug dan kesalahan. Misalnya, menambahkan fitur baru ke `CarService` akan memerlukan perubahan pada antarmuka dan semua implementasinya.
   - **Modifikasi yang telah dibuat:** Menggunakan antarmuka `CarService` yang dapat diimplementasikan oleh berbagai kelas, memungkinkan penambahan fitur baru tanpa mengubah kode yang ada.

4. **Poor Readability and Maintainability**
   - Tanpa SRP, kode menjadi sulit dibaca dan dipahami karena kelas dan metode memiliki terlalu banyak tanggung jawab. Ini membuat debugging dan pemeliharaan menjadi lebih sulit.
   - **Modifikasi yang telah dibuat:** Memisahkan tanggung jawab antara `CarController` dan `CarService`, serta membuat antarmuka `CarService` yang spesifik.
