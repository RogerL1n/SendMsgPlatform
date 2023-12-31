package com.lry.platform.webmaster.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAcountRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TAcountRecordExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNull() {
            addCriterion("orderid is null");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNotNull() {
            addCriterion("orderid is not null");
            return (Criteria) this;
        }

        public Criteria andOrderidEqualTo(String value) {
            addCriterion("orderid =", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotEqualTo(String value) {
            addCriterion("orderid <>", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThan(String value) {
            addCriterion("orderid >", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThanOrEqualTo(String value) {
            addCriterion("orderid >=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThan(String value) {
            addCriterion("orderid <", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThanOrEqualTo(String value) {
            addCriterion("orderid <=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLike(String value) {
            addCriterion("orderid like", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotLike(String value) {
            addCriterion("orderid not like", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidIn(List<String> values) {
            addCriterion("orderid in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotIn(List<String> values) {
            addCriterion("orderid not in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidBetween(String value1, String value2) {
            addCriterion("orderid between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotBetween(String value1, String value2) {
            addCriterion("orderid not between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andClientidIsNull() {
            addCriterion("clientId is null");
            return (Criteria) this;
        }

        public Criteria andClientidIsNotNull() {
            addCriterion("clientId is not null");
            return (Criteria) this;
        }

        public Criteria andClientidEqualTo(Long value) {
            addCriterion("clientId =", value, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidNotEqualTo(Long value) {
            addCriterion("clientId <>", value, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidGreaterThan(Long value) {
            addCriterion("clientId >", value, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidGreaterThanOrEqualTo(Long value) {
            addCriterion("clientId >=", value, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidLessThan(Long value) {
            addCriterion("clientId <", value, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidLessThanOrEqualTo(Long value) {
            addCriterion("clientId <=", value, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidIn(List<Long> values) {
            addCriterion("clientId in", values, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidNotIn(List<Long> values) {
            addCriterion("clientId not in", values, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidBetween(Long value1, Long value2) {
            addCriterion("clientId between", value1, value2, "clientid");
            return (Criteria) this;
        }

        public Criteria andClientidNotBetween(Long value1, Long value2) {
            addCriterion("clientId not between", value1, value2, "clientid");
            return (Criteria) this;
        }

        public Criteria andPaidvalueIsNull() {
            addCriterion("paidValue is null");
            return (Criteria) this;
        }

        public Criteria andPaidvalueIsNotNull() {
            addCriterion("paidValue is not null");
            return (Criteria) this;
        }

        public Criteria andPaidvalueEqualTo(Integer value) {
            addCriterion("paidValue =", value, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueNotEqualTo(Integer value) {
            addCriterion("paidValue <>", value, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueGreaterThan(Integer value) {
            addCriterion("paidValue >", value, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueGreaterThanOrEqualTo(Integer value) {
            addCriterion("paidValue >=", value, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueLessThan(Integer value) {
            addCriterion("paidValue <", value, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueLessThanOrEqualTo(Integer value) {
            addCriterion("paidValue <=", value, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueIn(List<Integer> values) {
            addCriterion("paidValue in", values, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueNotIn(List<Integer> values) {
            addCriterion("paidValue not in", values, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueBetween(Integer value1, Integer value2) {
            addCriterion("paidValue between", value1, value2, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andPaidvalueNotBetween(Integer value1, Integer value2) {
            addCriterion("paidValue not between", value1, value2, "paidvalue");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andPaytimeIsNull() {
            addCriterion("payTime is null");
            return (Criteria) this;
        }

        public Criteria andPaytimeIsNotNull() {
            addCriterion("payTime is not null");
            return (Criteria) this;
        }

        public Criteria andPaytimeEqualTo(Date value) {
            addCriterion("payTime =", value, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeNotEqualTo(Date value) {
            addCriterion("payTime <>", value, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeGreaterThan(Date value) {
            addCriterion("payTime >", value, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("payTime >=", value, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeLessThan(Date value) {
            addCriterion("payTime <", value, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeLessThanOrEqualTo(Date value) {
            addCriterion("payTime <=", value, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeIn(List<Date> values) {
            addCriterion("payTime in", values, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeNotIn(List<Date> values) {
            addCriterion("payTime not in", values, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeBetween(Date value1, Date value2) {
            addCriterion("payTime between", value1, value2, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaytimeNotBetween(Date value1, Date value2) {
            addCriterion("payTime not between", value1, value2, "paytime");
            return (Criteria) this;
        }

        public Criteria andPaymentidIsNull() {
            addCriterion("paymentId is null");
            return (Criteria) this;
        }

        public Criteria andPaymentidIsNotNull() {
            addCriterion("paymentId is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentidEqualTo(Integer value) {
            addCriterion("paymentId =", value, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidNotEqualTo(Integer value) {
            addCriterion("paymentId <>", value, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidGreaterThan(Integer value) {
            addCriterion("paymentId >", value, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidGreaterThanOrEqualTo(Integer value) {
            addCriterion("paymentId >=", value, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidLessThan(Integer value) {
            addCriterion("paymentId <", value, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidLessThanOrEqualTo(Integer value) {
            addCriterion("paymentId <=", value, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidIn(List<Integer> values) {
            addCriterion("paymentId in", values, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidNotIn(List<Integer> values) {
            addCriterion("paymentId not in", values, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidBetween(Integer value1, Integer value2) {
            addCriterion("paymentId between", value1, value2, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentidNotBetween(Integer value1, Integer value2) {
            addCriterion("paymentId not between", value1, value2, "paymentid");
            return (Criteria) this;
        }

        public Criteria andPaymentorderIsNull() {
            addCriterion("paymentOrder is null");
            return (Criteria) this;
        }

        public Criteria andPaymentorderIsNotNull() {
            addCriterion("paymentOrder is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentorderEqualTo(String value) {
            addCriterion("paymentOrder =", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderNotEqualTo(String value) {
            addCriterion("paymentOrder <>", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderGreaterThan(String value) {
            addCriterion("paymentOrder >", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderGreaterThanOrEqualTo(String value) {
            addCriterion("paymentOrder >=", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderLessThan(String value) {
            addCriterion("paymentOrder <", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderLessThanOrEqualTo(String value) {
            addCriterion("paymentOrder <=", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderLike(String value) {
            addCriterion("paymentOrder like", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderNotLike(String value) {
            addCriterion("paymentOrder not like", value, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderIn(List<String> values) {
            addCriterion("paymentOrder in", values, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderNotIn(List<String> values) {
            addCriterion("paymentOrder not in", values, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderBetween(String value1, String value2) {
            addCriterion("paymentOrder between", value1, value2, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentorderNotBetween(String value1, String value2) {
            addCriterion("paymentOrder not between", value1, value2, "paymentorder");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoIsNull() {
            addCriterion("paymentInfo is null");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoIsNotNull() {
            addCriterion("paymentInfo is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoEqualTo(String value) {
            addCriterion("paymentInfo =", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoNotEqualTo(String value) {
            addCriterion("paymentInfo <>", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoGreaterThan(String value) {
            addCriterion("paymentInfo >", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoGreaterThanOrEqualTo(String value) {
            addCriterion("paymentInfo >=", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoLessThan(String value) {
            addCriterion("paymentInfo <", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoLessThanOrEqualTo(String value) {
            addCriterion("paymentInfo <=", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoLike(String value) {
            addCriterion("paymentInfo like", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoNotLike(String value) {
            addCriterion("paymentInfo not like", value, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoIn(List<String> values) {
            addCriterion("paymentInfo in", values, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoNotIn(List<String> values) {
            addCriterion("paymentInfo not in", values, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoBetween(String value1, String value2) {
            addCriterion("paymentInfo between", value1, value2, "paymentinfo");
            return (Criteria) this;
        }

        public Criteria andPaymentinfoNotBetween(String value1, String value2) {
            addCriterion("paymentInfo not between", value1, value2, "paymentinfo");
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
