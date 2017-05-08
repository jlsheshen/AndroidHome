package com.edu.blankdemo;

/**
 * Created by Administrator on 2017/2/8.
 */

public class BlankData  {
    /**
     * 考试前查看答案模式
     */
    public static final int BEFORE_EXAM = 1;
    /**
     * 考试中答题模式
     */
    public static final int IN_EXAM = 2;
    /**
     * 考试后对比答案模式
     */
    public static final int AFTER_EXAM = 3;
    /**
     * 对比答案的答错状态
     */
    public static final int WRONG_ANSWER = 1;
    /**
     * 对比答案的答对状态
     */
    public static final int CORRECT_ANSWER = 1;
    /**
     * 对比答案的未作答
     */
    public static final int NULL_ANSWER = 1;

    /**
     * 题号
     */
    private int number;

    /**
     * 答题模式
     */
    private int pattern;
    /**
     * 答案状态
     */
    private int answerState;

    /**
     * 答案
     */
    private String answer;
    /**
     * 用户答案
     */
    private String userAnswer;

    public int getNumber() {
        return number;
    }

    public BlankData setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public BlankData setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public BlankData setAnswer(String answer) {
        this.answer = answer;
        return this;
    }


    public BlankData setPattern(int pattern) {
        this.pattern = pattern;
        return this;
    }

    public int getPattern() {
        return pattern;
    }

    public int getAnswerState() {
        return answerState;
    }

    public BlankData setAnswerState(int answerState) {
        this.answerState = answerState;
        return this;
    }
}
