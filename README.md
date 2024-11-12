# Rijks Museum Art Viewer

Rijks Museum Art Viewer is an Android app built with Kotlin and Jetpack Compose that allows users to explore the art collections from the Rijksmuseum. The app fetches data from the [Rijksmuseum API](https://data.rijksmuseum.nl/docs/api/), displays featured art pieces, provides detailed information for each artwork, and includes a fullscreen image viewer for a more immersive experience.

## Features
- **Featured Art:** Displays highlighted art pieces on the main screen.
- **Detailed View:** Allows users to view detailed information about each artwork.
- **Fullscreen Image Viewer:** Displays high-resolution artwork images in a fullscreen, distraction-free view.
- **Search for Authors:** Users can search for specific artists and view all their artworks.
- **Instrumented Tests:** Simple instrumented tests using MockK for isolated testing.

## Technologies Used
- **Jetpack Compose:** For building a responsive, modern UI with declarative components.
- **Coil:** For efficient image loading and caching of the artwork images.
- **Retrofit:** For making network requests to the Rijksmuseum API to retrieve artwork data.
- **Hilt:** For dependency injection.
- **Coroutines & Flow:** For handling asynchronous data loading and UI updates smoothly.
- **MockK:** For creating mocks in instrumented tests, enabling isolated testing of specific components.
- **Haze:** A library providing a 'glassmorphism' style blur effect for Compose.

## Getting Started

To run the app locally, clone this repository and follow these steps:

1. Open the project in Android Studio.
2. Build and run the app on an emulator or device.
3. Run the instrumented tests under androidTest.
