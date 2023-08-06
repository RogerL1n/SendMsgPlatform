package com.lry.platform.webmaster.pojo;

import java.util.ArrayList;
import java.util.List;

public class TNotifyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TNotifyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andDespIsNull() {
            addCriterion("desp is null");
            return (Criteria) this;
        }

        public Criteria andDespIsNotNull() {
            addCriterion("desp is not null");
            return (Criteria) this;
        }

        public Criteria andDespEqualTo(String value) {
            addCriterion("desp =", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespNotEqualTo(String value) {
            addCriterion("desp <>", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespGreaterThan(String value) {
            addCriterion("desp >", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespGreaterThanOrEqualTo(String value) {
            addCriterion("desp >=", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespLessThan(String value) {
            addCriterion("desp <", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespLessThanOrEqualTo(String value) {
            addCriterion("desp <=", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespLike(String value) {
            addCriterion("desp like", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespNotLike(String value) {
            addCriterion("desp not like", value, "desp");
            return (Criteria) this;
        }

        public Criteria andDespIn(List<String> values) {
            addCriterion("desp in", values, "desp");
            return (Criteria) this;
        }

        public Criteria andDespNotIn(List<String> values) {
            addCriterion("desp not in", values, "desp");
            return (Criteria) this;
        }

        public Criteria andDespBetween(String value1, String value2) {
            addCriterion("desp between", value1, value2, "desp");
            return (Criteria) this;
        }

        public Criteria andDespNotBetween(String value1, String value2) {
            addCriterion("desp not between", value1, value2, "desp");
            return (Criteria) this;
        }

        public Criteria andNotifyStateIsNull() {
            addCriterion("notify_state is null");
            return (Criteria) this;
        }

        public Criteria andNotifyStateIsNotNull() {
            addCriterion("notify_state is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyStateEqualTo(Short value) {
            addCriterion("notify_state =", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateNotEqualTo(Short value) {
            addCriterion("notify_state <>", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateGreaterThan(Short value) {
            addCriterion("notify_state >", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateGreaterThanOrEqualTo(Short value) {
            addCriterion("notify_state >=", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateLessThan(Short value) {
            addCriterion("notify_state <", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateLessThanOrEqualTo(Short value) {
            addCriterion("notify_state <=", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateIn(List<Short> values) {
            addCriterion("notify_state in", values, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateNotIn(List<Short> values) {
            addCriterion("notify_state not in", values, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateBetween(Short value1, Short value2) {
            addCriterion("notify_state between", value1, value2, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateNotBetween(Short value1, Short value2) {
            addCriterion("notify_state not between", value1, value2, "notifyState");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
