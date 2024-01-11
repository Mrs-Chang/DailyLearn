package com.chang.dailylearn.words

fun minEditDistance(except: CharSequence, actual: CharSequence): List<EditStep>? {
    if (except.isEmpty() && actual.isEmpty()) {
        return null
    }
    val editDp = Array(except.length + 1) {
        Array(actual.length + 1) {
            EditStep(EditAction.INIT, 0, null)
        }
    }
    //第一行: 表示except 与空串的编辑距离
    for (row in 1..actual.length) {
        editDp[0][row] = EditStep(EditAction.DELETE, row, editDp[0][row - 1])
    }
    //第一列: 空串 与 actual 的编辑距离
    for (col in 1..except.length) {
        editDp[col][0] = EditStep(EditAction.INSERT, col, editDp[col - 1][0])
    }

    for (row in 1..except.length) {
        for (col in 1..actual.length) {
            if (Character.toLowerCase(except[row - 1]) == Character.toLowerCase(actual[col - 1])) {
                val noopEditStep = editDp[row - 1][col - 1]
                editDp[row][col] = EditStep(EditAction.NOOP, noopEditStep.distance, noopEditStep)
            } else {
                val replaceEditStep = editDp[row - 1][col - 1]
                val insertEditStep = editDp[row - 1][col]
                val deleteEditStep = editDp[row][col - 1]
                editDp[row][col] = minOfEditStep(replaceEditStep, insertEditStep, deleteEditStep)
            }
        }
    }
    val editSteps = ArrayList<EditStep>()
    var lastStep: EditStep? = editDp[except.length][actual.length]
    while (lastStep != null && lastStep.action != EditAction.INIT) {
        editSteps.add(lastStep)
        lastStep = lastStep.prev
    }
    return editSteps.asReversed()
}

private fun minOfEditStep(
    replaceEditStep: EditStep,
    insertEditStep: EditStep,
    deleteEditStep: EditStep
): EditStep {
    return if (replaceEditStep.distance <= insertEditStep.distance && replaceEditStep.distance <= deleteEditStep.distance) {
        EditStep(EditAction.REPLACE, replaceEditStep.distance + 1, replaceEditStep)
    } else if (insertEditStep.distance <= replaceEditStep.distance && insertEditStep.distance <= deleteEditStep.distance) {
        EditStep(EditAction.INSERT, insertEditStep.distance + 1, insertEditStep)
    } else {
        EditStep(EditAction.DELETE, deleteEditStep.distance + 1, deleteEditStep)
    }
}
