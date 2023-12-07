package com.chang.dailylearn.words

/**
 * INIT：初始状态
 * NOOP：不操作
 * INSERT：插入
 * DELETE：删除
 * REPLACE：替换
 * */
enum class EditAction {
    INIT, NOOP, INSERT, DELETE, REPLACE
}