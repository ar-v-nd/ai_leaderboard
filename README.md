# LeaderBoard Android Application

A modern Android application that displays a leaderboard of AI models, built using Kotlin, MVVM architecture, and Jetpack components.

## Features

- **Dynamic Leaderboard**: Displays a list of AI models sorted by rating.
- **Top 3 Highlight**: A specialized UI section to prominently feature the top 3 ranked models.
- **Responsive UI**: Optimized for landscape orientation with specialized layouts.
- **Robust Error Handling**: Standardized Material Design dialogs for network errors, API failures, and empty data states.
- **Swipe-to-Refresh**: Easily update the leaderboard data with a pull-to-refresh gesture.
- **Adaptive Sizing**: Uses `sdp` and `ssp` libraries for scalable dimensions across different screen sizes.

## Architecture

The project follows the **MVVM (Model-View-ViewModel)** architectural pattern:

- **View**: Fragments (`SplashFragment`, `RankingFragment`) handle UI logic and observe LiveData from ViewModels.
- **ViewModel**: `MainViewModel` and `SplashViewModel` manage UI state and coordinate data fetching.
- **Model**: `AiDataModel` represents the domain data, while `LeaderBoardItem` represents the API response.
- **Repository**: `LeaderBoardRepository` abstracts the data source (Remote API via Retrofit).
- **Mappers**: `LeaderBoardMapper` ensures a clean separation between network models and UI models.

## Tech Stack

- **Language**: Kotlin
- **Asynchronous Programming**: Coroutines & Flow
- **Networking**: Retrofit 2 & OkHttp
- **JSON Serialization**: Kotlinx Serialization
- **Image Loading**: Glide
- **DI/Management**: MVVM with Shared ViewModels (Activity-scoped)
- **UI Components**:
    - Data Binding
    - ConstraintLayout
    - RecyclerView
    - SwipeRefreshLayout
    - Material Design 3 Components
    - Navigation Component
- **Unit Testing**: JUnit, Mockito, and Coroutines Test library.

## Project Structure

- `ui`: Contains fragments and adapters for Splash and Ranking screens.
- `viewmodel`: Contains ViewModels managing the app state.
- `data`: Contains API service, repositories, and data models.
- `util`: Contains utility classes and binding adapters.
- `res/layout`: Includes optimized XML layouts for both portrait and landscape (mostly landscape focused).

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.
4. Ensure internet connectivity to fetch the leaderboard data.

## License

This project is developed for demonstration purposes.
