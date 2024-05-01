package com.example.androidlesson7.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
public data class User (var username: String, var rating: Double, var gender:String, var registrationDate:String, var imagePath:String): Parcelable
