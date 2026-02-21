# CleanRmApiUDF

Kotlin Multiplatform educational project used to demonstrate:
- a practical Clean Architecture implementation
- a multi-module KMP organization
- adaptive UI orchestration (portrait/landscape) with reusable components

The app consumes the Rick and Morty API and is split into `core` and `features` modules to keep dependencies explicit and controlled.

## Pedagogical goals

This repository is designed to show how to:
- enforce architecture boundaries through Gradle modules
- keep business logic independent from UI/framework details
- build reusable UI components while preserving screen-level orchestration control
- target multiple platforms (Android, iOS, Desktop, Wasm/Web) from one shared codebase

## Tech stack

- Kotlin Multiplatform (KMP)
- Compose Multiplatform
- Koin (DI)
- Ktor (networking)
- Room / local persistence (non-JS targets)
- Navigation 3 + lifecycle-viewmodel-navigation3

## Module structure

```text
:composeApp                        -> application entry point (Android/iOS/Desktop/Wasm)
:core:app                          -> app shell, root NavDisplay, app back stack
:core:common                       -> shared utilities and contracts
:core:data                         -> shared data infrastructure
:core:domain                       -> pure business models/contracts
:core:navigation                   -> navigation abstractions
:core:presentation                 -> shared UI primitives (Screen, Store, UiComponent, NavScreen)

:features:characters:api           -> public API for Characters feature
:features:characters:domain        -> Characters business contracts/models
:features:characters:data          -> Characters data implementation
:features:characters:navigation    -> Characters navigation contracts
:features:characters:presentation  -> Characters UI

:features:episodes:api             -> public API for Episodes feature
:features:episodes:domain          -> Episodes business contracts/models
:features:episodes:data            -> Episodes data implementation
:features:episodes:navigation      -> Episodes navigation contracts
:features:episodes:presentation    -> Episodes UI

:iosApp                            -> native iOS entry point
```

## Clean Architecture mapping

- `domain` modules:
  - business models
  - repository contracts
  - no UI/framework dependency

- `data` modules:
  - repository implementations
  - remote/local orchestration
  - depends on `domain`, not on `presentation`

- `presentation` modules:
  - screens, components, view models, store/actions
  - translates UI intents into feature use cases

- `navigation` modules:
  - feature-specific navigation contracts
  - screen registration in graphs

- `core:app` module:
  - global router and global back stack
  - root `NavDisplay`
  - app bootstrap

## UI architecture used

The project follows a "screen-orchestrated components" model:

- one ViewModel per displayed screen
- each component owns its `UiState` and `UiAction`
- the screen maps component actions to screen actions
- stores/actions drive state transitions and side effects

Pattern:

```text
Component -> Screen -> ViewModel -> Store -> StoreAction
```

Detailed explanation:
- `docs/ui-architecture.md`

## Adaptive demonstration (Characters feature)

`CharactersEntryScreen` acts as an orchestrator with an internal back stack:

- horizontal mode:
  - shows `CharactersDashboard`
  - list + details are visible at the same time

- vertical mode:
  - starts on `CharactersList`
  - selecting a character pushes `CharacterDetails`

Demonstrated behavior:

1. Horizontal `[Dashboard + selected character]`
   -> rotate to vertical
   -> internal stack `[CharactersList, CharacterDetails]`

2. Horizontal `[Dashboard + no selected character]`
   -> rotate to vertical
   -> internal stack `[CharactersList]`
   -> select a character
   -> internal stack `[CharactersList, CharacterDetails]`
   -> rotate back to horizontal
   -> dashboard keeps selected character

## Build and verification

Useful commands:

```bash
./gradlew :features:characters:presentation:compileCommonMainKotlinMetadata
./gradlew :features:characters:presentation:compileAndroidMain
./gradlew :core:app:compileAndroidMain
./gradlew :composeApp:compileDebugKotlinAndroid
```

Note:
- `:composeApp:assemble` may require additional JS/Wasm setup depending on the branch state.

## Suggested walkthrough

Recommended order to present the project:

1. Start from `settings.gradle.kts` and the module graph
2. Follow one full vertical slice:
   `features:characters:domain -> data -> presentation`
3. Present `core:presentation` primitives (`Screen`, `Store`, `UiComponent`, `NavScreen`)
4. Demonstrate adaptive orchestration in `CharactersEntryScreen`
5. Compare portrait/landscape behavior without rewriting reusable components

-------------------

# CleanRmApiUDF

Projet pedagogique Kotlin Multiplatform utilise pour demontrer:
- une implementation pratique de la Clean Architecture
- une organisation KMP multi-modulaire
- une orchestration UI adaptive (portrait/paysage) avec composants reutilisables

L'application consomme l'API Rick and Morty et est decoupee en modules `core` et `features` pour garder des dependances explicites et maitrisees.

## Objectifs pedagogiques

Ce repository est concu pour montrer comment:
- imposer des frontieres d'architecture via les modules Gradle
- garder la logique metier independante des details UI/framework
- creer des composants UI reutilisables sans perdre le controle d'orchestration au niveau ecran
- cibler plusieurs plateformes (Android, iOS, Desktop, Wasm/Web) depuis une base partagee

## Stack technique

- Kotlin Multiplatform (KMP)
- Compose Multiplatform
- Koin (DI)
- Ktor (reseau)
- Room / persistence locale (cibles non-JS)
- Navigation 3 + lifecycle-viewmodel-navigation3

## Structure des modules

```text
:composeApp                        -> point d'entree applicatif (Android/iOS/Desktop/Wasm)
:core:app                          -> shell applicatif, NavDisplay racine, back stack applicative
:core:common                       -> utilitaires et contrats partages
:core:data                         -> infrastructure data partagee
:core:domain                       -> modeles/contrats metier purs
:core:navigation                   -> abstractions de navigation
:core:presentation                 -> primitives UI partagees (Screen, Store, UiComponent, NavScreen)

:features:characters:api           -> API publique de la feature Characters
:features:characters:domain        -> contrats/modeles metier Characters
:features:characters:data          -> implementation data Characters
:features:characters:navigation    -> contrats de navigation Characters
:features:characters:presentation  -> UI Characters

:features:episodes:api             -> API publique de la feature Episodes
:features:episodes:domain          -> contrats/modeles metier Episodes
:features:episodes:data            -> implementation data Episodes
:features:episodes:navigation      -> contrats de navigation Episodes
:features:episodes:presentation    -> UI Episodes

:iosApp                            -> point d'entree natif iOS
```

## Mapping Clean Architecture

- modules `domain`:
  - modeles metier
  - contrats de repositories
  - aucune dependance UI/framework

- modules `data`:
  - implementations de repositories
  - orchestration remote/local
  - depend de `domain`, pas de `presentation`

- modules `presentation`:
  - screens, components, view models, store/actions
  - traduit les intentions UI en cas d'usage de feature

- modules `navigation`:
  - contrats de navigation par feature
  - enregistrement des ecrans dans les graphes

- module `core:app`:
  - routeur global et back stack globale
  - `NavDisplay` racine
  - bootstrap applicatif

## Architecture UI retenue

Le projet suit un modele "screen-orchestrated components":

- un ViewModel par ecran affiche
- chaque composant possede son `UiState` et ses `UiAction`
- l'ecran mappe les actions composant vers des actions ecran
- les stores/actions pilotent transitions d'etat et side effects

Pattern:

```text
Component -> Screen -> ViewModel -> Store -> StoreAction
```

Explication detaillee:
- `docs/ui-architecture.md`

## Demonstration adaptive (feature Characters)

`CharactersEntryScreen` joue le role d'orchestrateur avec une back stack interne:

- mode horizontal:
  - affiche `CharactersDashboard`
  - liste + details visibles en meme temps

- mode vertical:
  - demarre sur `CharactersList`
  - selectionner un personnage pousse `CharacterDetails`

Regle de gestion demontree:

1. Horizontal `[Dashboard + personnage selectionne]`
   -> rotation verticale
   -> stack interne `[CharactersList, CharacterDetails]`

2. Horizontal `[Dashboard + aucun personnage selectionne]`
   -> rotation verticale
   -> stack interne `[CharactersList]`
   -> selection d'un personnage
   -> stack interne `[CharactersList, CharacterDetails]`
   -> rotation horizontale
   -> retour dashboard avec selection conservee

## Build et verification

Commandes utiles:

```bash
./gradlew :features:characters:presentation:compileCommonMainKotlinMetadata
./gradlew :features:characters:presentation:compileAndroidMain
./gradlew :core:app:compileAndroidMain
./gradlew :composeApp:compileDebugKotlinAndroid
```

Note:
- `:composeApp:assemble` peut necessiter une configuration JS/Wasm supplementaire selon l'etat de la branche.

## Parcours recommande

Ordre conseille pour presenter le projet:

1. Partir de `settings.gradle.kts` et du graphe de modules
2. Suivre une slice verticale complete:
   `features:characters:domain -> data -> presentation`
3. Presenter les primitives `core:presentation` (`Screen`, `Store`, `UiComponent`, `NavScreen`)
4. Demonstrer l'orchestration adaptive dans `CharactersEntryScreen`
5. Comparer portrait/paysage sans re-ecrire les composants reutilisables
