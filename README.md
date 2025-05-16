📱 Wildlens – Application Android
🚀 Présentation

Wildlens est une application Android développée avec Jetpack Compose. 
Elle s’appuie sur une architecture moderne et modulaire, conçue pour la maintenabilité, la scalabilité et une excellente expérience développeur.
🛠️ Technologies utilisées

    Kotlin

    Jetpack Compose – UI déclarative moderne

    Navigation Compose – gestion de la navigation entre les écrans

    Firebase Authentication – authentification sécurisée (email / mot de passe)

    Hilt – injection de dépendances simple et puissante

    StateFlow / SharedFlow – gestion des flux d’état et d’événements

    CameraX – gestion de la prise de photo

    libs.versions.toml – gestion centralisée des versions et dépendances (version catalog)

🧱 Architecture
🧭 MVI (Model – View – Intent)

Chaque écran suit une architecture MVI :

    State : décrit l’état de l’écran (Loading, Success, Error)

    Action : représente les intentions de la vue (clic, scroll, etc.)

    UiEvent : effets ponctuels comme snackbars ou scroll

    NavigationEvent : événements de navigation vers d’autres écrans

    ViewModel : cœur logique de l’écran, gère les actions, modifie l’état, émet les événements

🗂 Structure du projet (par feature)

Organisation en features modulaires dans ui/screens/ :

    home/

    fav/

    login/

Chaque feature contient :

    presentation/ : fichiers liés à la UI (Screen, ScreenSuccess)

    state/ : ViewModel, State, Action, Events

Exemple :

screens/  
└── home/  
├── presentation/  
│   ├── HomeScreen.kt  
│   ├── HomeScreenSuccess.kt  
├── state/  
│   ├── HomeViewModel.kt  
│   ├── HomeState.kt  
│   ├── HomeAction.kt  
│   ├── HomeUiEvent.kt  
│   ├── HomeNavigationEvent.kt  


💡 Bonnes pratiques adoptées

    Clean code : code clair, découpé et testable

    Feature-first : découpage par fonctionnalité, prêt pour la modularisation

    Separation of concerns : la UI, les états, et la logique métier sont bien séparés

    Scaffold personnalisé : WildlensScaffold pour uniformiser les écrans

🔐 Authentification Firebase

    Méthode : Email / Mot de passe

    Sécurisée et gratuite (Firebase Free Tier)

    Gestion de session via FirebaseAuth.getInstance().currentUser

🧪 Tests

    ViewModel testables grâce à StateFlow

    Tests unitaires (JUnit)

    Test UI à venir (Compose Testing Library)

