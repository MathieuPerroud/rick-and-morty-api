# UI Architecture: Screen-Orchestrated Components

## Overview

This project uses a **screen-orchestrated UI architecture**:

- One `ViewModel` per displayed screen.
- UI components own their **local UI contracts** (`UiState` + `UiAction`).
- Screen-level orchestration maps component actions to screen actions, then delegates to the screen `ViewModel`.

This keeps screens responsible for lifecycle and navigation decisions, while components stay reusable and focused on rendering + interaction intent.

## Core Principles

1. **One ViewModel per Screen**
- A screen is the lifecycle holder.
- The screen `ViewModel` owns screen state, side effects, and external dependencies.

2. **Component-Scoped UI Contracts**
- Each component defines its own `UiState` and `UiAction`.
- Components do not decide app-level behavior (navigation, cross-feature flows, etc.).

3. **Action Translation at Screen Level**
- A component emits a local action (for example `OnItemClicked`).
- The screen translates it into a screen action/event.
- The screen `ViewModel` interprets this action according to the current layout and context.

4. **Context-Dependent Interpretation**
- The same component action can be interpreted differently depending on the hosting screen.

## Why This Improves Modularity

- Components remain portable across screens and layouts.
- Business and navigation decisions are centralized in screen ViewModels.
- UI contracts are explicit and testable at both component and screen levels.
- Layout adaptations (portrait vs landscape, phone vs tablet) do not require rewriting components.

## Example: Same Action, Different Behavior

Consider a `ListOfCharacters` component emitting `OnCharacterClicked(character)`.

### Portrait flow
- Screen receives `OnCharacterClicked`.
- Screen maps it to a screen action (for example `OpenCharacterDetails(character.id)`).
- Screen `ViewModel` triggers navigation to `CharacterDetailsScreen`.

### Landscape dashboard flow
- Screen receives `OnCharacterClicked`.
- Screen maps to a screen action (for example `SelectCharacter(character.id)`).
- Screen `ViewModel` updates state so details are rendered on the right panel in the same page.

Result: one reusable list component, two valid behaviors, no duplication of component logic.

## Practical Guidelines

- Keep component actions intention-based (`Clicked`, `Changed`, `Requested`) and context-agnostic.
- Keep navigation logic out of components.
- Keep mapping logic in screens, close to orchestration concerns.
- Keep state boundaries clear:
  - component state for rendering details,
  - screen state for composition, routing, and cross-component coordination.
