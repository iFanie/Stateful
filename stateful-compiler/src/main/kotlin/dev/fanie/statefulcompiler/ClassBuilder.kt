package dev.fanie.statefulcompiler

interface ClassBuilder {
    val classPackage: String
    val className: String
    val classSource: String
}