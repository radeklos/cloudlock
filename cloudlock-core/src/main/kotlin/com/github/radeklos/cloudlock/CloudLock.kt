package com.github.radeklos.cloudlock

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.ANNOTATION_CLASS
)
@Retention(AnnotationRetention.RUNTIME)
annotation class CloudLock(
    /**
     * Lock name.
     */
    val name: String = "",
)
