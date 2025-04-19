# 🧭 Architecture de Navigation

Le projet suit une architecture modulaire et découplée basée sur Jetpack Compose Navigation, intégrée à un modèle MVI (Model–View–Intent).
Chaque feature gère sa propre logique de navigation à travers une structure claire et évolutive.

# 🔁 Flow de navigation
UI (clic bouton)
↓
onAction(Action)
↓
ViewModel → emit(NavigationEvent)
↓
Composable → collect(NavigationEvent)
↓
NavController.navigate(route)
↓
NavHost → composable(route) → nouvel écran affiché 

# 📁 Organisation

### Chaque feature (login, home, profile, etc.) contient :

/ui/screens/feature/  
├── presentation/  
│   └── FeatureScreen.kt  
├── state/  
│   ├── FeatureViewModel.kt  
│   ├── FeatureState.kt  
│   ├── FeatureAction.kt  
│   ├── FeatureNavigationEvent.kt  
├── navigation/  
│   └── FeatureNavigation.kt ← fonction `featureScreen(navController)`  

# 🧩 Exemple
### LoginAction.kt
```kotlin
sealed interface LoginAction {
    object OnLoginClicked : LoginAction
    object OnRegisterClicked : LoginAction
}
```
### LoginViewModel.kt
```kotlin
fun onAction(action: LoginAction) {
    when (action) {
        OnLoginClicked -> emitNavigation(LoginNavigationEvent.NavigateToHome)
        OnRegisterClicked -> emitNavigation(LoginNavigationEvent.NavigateToRegister)
    }
}
```
### loginScreen(navController)
```kotlin
LaunchedEffect(Unit) {
    viewModel.navigationEvent.collect { event ->
        when (event) {
            NavigateToHome -> navController.navigate(Screen.Home.route)
        }
    }
}
```
# ✅ Avantages

- Architecture scalable : chaque feature gère son propre graphe

- Navigation découplée : aucun lien direct entre UI et NavController

- Testabilité accrue : les ViewModel sont isolés et mockables

- Lisibilité : chaque NavGraphBuilder.featureScreen() encapsule son comportement