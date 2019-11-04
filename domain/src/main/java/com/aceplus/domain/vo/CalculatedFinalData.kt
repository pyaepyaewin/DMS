package com.aceplus.domain.vo

data class CalculatedFinalData(

    var amountAndPercentage: MutableMap<String, Double>,
    var totalVolumeDiscount: Double,
    var totalVolumeDiscountPercent: Double,
    var taxType: String,
    var taxPercent: Int

)