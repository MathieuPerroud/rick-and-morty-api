package org.mathieu.cleanrmapi.ui.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Scope-internal operations over the back stack held by [AppScopeViewModel].
 *
 * The router type never leaves the navigation package: composables and feature contracts only
 * see the intentions declared under `presentation/navigation`, which the scope implements over
 * this router. It therefore never appears in a composable, a contract signature or an ambient.
 */
internal class AppRouter(private val stack: SnapshotStateList<NavScreen>) {

    /** Appends [screen] on top of the stack. */
    fun push(screen: NavScreen) {
        stack.add(screen)
    }

    /** Replaces the top entry with [screen], or appends it when the stack is empty. */
    fun replaceTop(screen: NavScreen) {
        if (stack.isEmpty()) {
            stack.add(screen)
        } else {
            stack[stack.lastIndex] = screen
        }
    }

    /**
     * Pops the top entry. Guarded: while only the root screen is on the stack (depth one) this
     * is a no-op, so the stack stays non-empty while the application renders.
     */
    fun pop() {
        if (stack.size > 1) {
            stack.removeAt(stack.lastIndex)
        }
    }
}
