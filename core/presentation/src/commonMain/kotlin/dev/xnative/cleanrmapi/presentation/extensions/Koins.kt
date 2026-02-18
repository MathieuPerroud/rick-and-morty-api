package dev.xnative.cleanrmapi.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import org.koin.compose.currentKoinScope
import org.koin.compose.koinInject
import org.koin.core.Koin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

val IsInPreviewMode: Boolean
    @Composable
    get() = LocalInspectionMode.current

val fakeScope = Scope(
    scopeQualifier = named("fakeScope"),
    id = "fakeScopeId",
    isRoot = false,
    _koin = Koin()
)

@Composable
inline fun <reified T> koinInject(
    valueForPreview: T,
    qualifier: Qualifier? = null,
    scope: Scope = if (IsInPreviewMode) fakeScope else currentKoinScope(),
    noinline parameters: ParametersDefinition = { ParametersHolder() }
): T {

    if (IsInPreviewMode) return valueForPreview

    return koinInject(qualifier, scope, parameters)
}