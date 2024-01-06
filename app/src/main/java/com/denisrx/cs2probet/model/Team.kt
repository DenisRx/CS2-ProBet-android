package com.denisrx.cs2probet.model

data class Team(
    val points: Int,
    val place: Int,
    val name: String,
    val id: Int,
    val change: Int,
    val isNew: Boolean,
    val isSelected: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Team) return false

        return points == other.points
                && place == other.place
                && name == other.name
                && id == other.id
                && change == other.change
                && isNew == other.isNew
    }

    override fun hashCode(): Int {
        var result = points
        result = 31 * result + place
        result = 31 * result + name.hashCode()
        result = 31 * result + id
        result = 31 * result + change
        result = 31 * result + isNew.hashCode()
        return result
    }
}
