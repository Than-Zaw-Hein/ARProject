package com.tzh.arproject.solarSystem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.ar.core.Frame
import com.google.ar.core.Session
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener

@Composable
fun SolarSystemScreen(arModel: String) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val model = rememberNode {
        ModelNode(
            modelInstance = modelLoader.createModelInstance(
                assetFileLocation = arModel
            ), scaleToUnits = 1.0f
        )
    }
    val childNodes = rememberNodes { }
    var frame by remember { mutableStateOf<Frame?>(null) }

    LaunchedEffect(frame) {
        val hitResults = frame?.hitTest(0.5f, 0f)
        val anchor = hitResults?.firstOrNull {
            it.isValid(depthPoint = false, point = false)
        }?.createAnchorOrNull()

        if (anchor != null) {
            val anchorNode = AnchorNode(engine = engine, anchor = anchor)
            anchorNode.addChildNode(model)
            childNodes += anchorNode
        }
    }
    // Create a mutable reference to hold the internal SceneView ARSession
    var arSessionRef by remember { mutableStateOf<Session?>(null) }

    // Use DisposableEffect to ensure the session is destroyed when the Composable leaves
    DisposableEffect(Unit) {
        onDispose {
            // Explicitly destroy the AR session when navigating away
            arSessionRef?.close()
        }
    }
    ARScene(
        modifier = Modifier.fillMaxSize(),
        engine = engine,
        modelLoader = modelLoader,
        childNodes = childNodes,
        onViewCreated = { },
        onSessionUpdated = { session, updatedFrame ->
            arSessionRef = session
            frame = updatedFrame
        },
        onGestureListener = rememberOnGestureListener(
            onSingleTapConfirmed = { motionEvent, node ->
                val hitResults = frame?.hitTest(motionEvent.x, motionEvent.y)
                val anchor = hitResults?.firstOrNull {
                    it.isValid(depthPoint = false, point = false)
                }?.createAnchorOrNull()

                if (anchor != null) {
                    val anchorNode = AnchorNode(engine = engine, anchor = anchor)
                    anchorNode.addChildNode(model)
                    childNodes += anchorNode
                }
            },
        )
    )
}
