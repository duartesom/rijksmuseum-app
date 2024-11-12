package com.ticketswapassessment.ui.image_viewer

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor() : ViewModel() {
    private val _imageViewerModel = MutableStateFlow(ImageViewerModel())
    val imageViewerModel = _imageViewerModel.asStateFlow()

    fun setImageViewerData(id: String?, url: String?, imageBitmap: ImageBitmap){
        _imageViewerModel.update { ImageViewerModel(id, url, imageBitmap) }
    }
}

data class ImageViewerModel(
    val id: String? = "",
    val url: String? = null,
    val bitmap: ImageBitmap? = null
)