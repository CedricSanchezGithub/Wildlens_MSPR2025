# 📸 WildLens – Animal Footprint Recognition App

WildLens is a mobile Android application that leverages **machine learning** to help users identify animal species from **footprint images**. Designed for nature enthusiasts, researchers, and students, the app guides users through a quick scanning process and provides real-time predictions.

---

## 🚀 Features

- 📷 **Camera integration** for footprint capture
- 🧠 **On-device AI inference** using TensorFlow Lite
- 🔍 **Animal species identification** via a classification model
- 📖 **Information cards** for known species (e.g. lifestyle, habitat)
- 🧪 Offline prediction + online metadata fetch via **Firestore**
- 🛠️ Developed with **Jetpack Compose**, **MVVM architecture** and **Hilt DI**

---

## 🧱 Architecture Overview

WildLens follows a **clean MVVM architecture** with the following layers:

```
View (Jetpack Compose)
     ↓
ViewModel (State & Events, DI with Hilt)
     ↓
UseCases (e.g. LoadModel, ClassifyImage)
     ↓
Repositories (e.g. MetadataRepository, InferenceRepository)
     ↓
Data Sources (Firestore + Local assets)
```

### ✅ Main Technologies

| Area              | Technology / Library                                      |
|-------------------|-----------------------------------------------------------|
| UI                | Jetpack Compose, Material 3, Navigation Compose           |
| State Management  | ViewModel, StateFlow, LiveData                            |
| Dependency Injection | Hilt + KSP                                              |
| ML Inference      | TensorFlow Lite, TFLite Support & Task Library            |
| Backend           | Firebase Firestore (metadata + species info)              |
| Camera            | CameraX                                                   |
| Permissions       | Accompanist Permissions                                   |
| Serialization     | Kotlinx Serialization + Retrofit                          |
| Testing           | JUnit, MockK, Coroutine Test, Espresso                    |

---

## 🧪 Testing Strategy

- **Unit tests** with `JUnit4`, `MockK`, and `kotlinx.coroutines.test`
- **UI tests** using `Espresso` and `Compose Test`
- Instrumentation runner: `androidx.test.runner.AndroidJUnitRunner`

---

## 📁 Modules & Packages

- `ui/`: Composables and screens
- `viewmodel/`: ViewModels handling state & actions
- `data/`: Data sources and repositories (Firestore, model files)
- `ml/`: ML model loader and inference logic
- `di/`: Hilt modules for dependency injection
- `utils/`: Helper functions and extensions

---

## 📷 Screenshots (TODO)

Add screenshots demonstrating:
- Camera capture
- Prediction result
- Species detail view

---

## 📦 Build & Run

Make sure the following SDKs are installed:

- **minSdk**: 24
- **targetSdk**: 34
- **compileSdk**: 35

Then run:

```bash
./gradlew installDebug
```

---

## 🔐 Permissions Used

- `CAMERA` – For image capture
- `INTERNET` – To retrieve metadata from Firebase
- `READ_EXTERNAL_STORAGE` – For image access (if implemented)

---

## 🌍 Multilingual

Support planned for:
- 🇫🇷 French (default)
- 🇬🇧 English

---

## 🧠 AI Model Details

- Format: TensorFlow Lite
- Input: 224x224 RGB image
- Output: Top-K class prediction with confidence
- Model loaded from assets and run via `TFLiteTask`

---

## 🛠️ Future Enhancements

- Export results and track observations
- Map view with geolocation support
- More detailed species info from external APIs

---

## 📚 License

Project for educational purposes under the DEVIA 2024–2025 MSPR program.

---
