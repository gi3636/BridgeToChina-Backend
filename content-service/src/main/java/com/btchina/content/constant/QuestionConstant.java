package com.btchina.content.constant;

public class QuestionConstant {
    public static final String INDEX = "question";
    public static final String EXCHANGE_NAME = "question.topic";
    public static final String INSERT_QUEUE_NAME = "question.insert.queue";
    public static final String UPDATE_QUEUE_NAME = "question.update.queue";
    public static final String DELETE_QUEUE_NAME = "question.delete.queue";
    public static final String INSERT_KEY = "question.insert";
    public static final String DELETE_KEY = "question.delete";
    public static final String UPDATE_KEY = "question.update";
    public static final String USER_question_LIKE_KEY = "user::question.like";
    public static final String USER_question_LIKE_COUNT_KEY = "user::question.like.count";
    public static final String USER_question_FAVORITE_KEY = "user::question.favorite";
    public static final String USER_question_FAVORITE_COUNT_KEY = "user::question.favorite.count";

    public static final String DECREASE_FAVOURITE_COUNT_QUEUE_NAME = "question.decrease.favourite.count.queue";
    public static final String DECREASE_FAVOURITE_COUNT_ROUTING_KEY = "question.decrease.favourite.count";
    public static final String INCREASE_FAVOURITE_COUNT_QUEUE_NAME = "question.increase.favourite.count.queue";
    public static final String INCREASE_FAVOURITE_COUNT_ROUTING_KEY = "question.increase.favourite.count.increase";
}
