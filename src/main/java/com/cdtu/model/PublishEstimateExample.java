package com.cdtu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PublishEstimateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PublishEstimateExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andEpIdIsNull() {
            addCriterion("ep_id is null");
            return (Criteria) this;
        }

        public Criteria andEpIdIsNotNull() {
            addCriterion("ep_id is not null");
            return (Criteria) this;
        }

        public Criteria andEpIdEqualTo(String value) {
            addCriterion("ep_id =", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdNotEqualTo(String value) {
            addCriterion("ep_id <>", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdGreaterThan(String value) {
            addCriterion("ep_id >", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdGreaterThanOrEqualTo(String value) {
            addCriterion("ep_id >=", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdLessThan(String value) {
            addCriterion("ep_id <", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdLessThanOrEqualTo(String value) {
            addCriterion("ep_id <=", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdLike(String value) {
            addCriterion("ep_id like", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdNotLike(String value) {
            addCriterion("ep_id not like", value, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdIn(List<String> values) {
            addCriterion("ep_id in", values, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdNotIn(List<String> values) {
            addCriterion("ep_id not in", values, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdBetween(String value1, String value2) {
            addCriterion("ep_id between", value1, value2, "epId");
            return (Criteria) this;
        }

        public Criteria andEpIdNotBetween(String value1, String value2) {
            addCriterion("ep_id not between", value1, value2, "epId");
            return (Criteria) this;
        }

        public Criteria andTscIdIsNull() {
            addCriterion("tsc_id is null");
            return (Criteria) this;
        }

        public Criteria andTscIdIsNotNull() {
            addCriterion("tsc_id is not null");
            return (Criteria) this;
        }

        public Criteria andTscIdEqualTo(Integer value) {
            addCriterion("tsc_id =", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdNotEqualTo(Integer value) {
            addCriterion("tsc_id <>", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdGreaterThan(Integer value) {
            addCriterion("tsc_id >", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tsc_id >=", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdLessThan(Integer value) {
            addCriterion("tsc_id <", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdLessThanOrEqualTo(Integer value) {
            addCriterion("tsc_id <=", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdIn(List<Integer> values) {
            addCriterion("tsc_id in", values, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdNotIn(List<Integer> values) {
            addCriterion("tsc_id not in", values, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdBetween(Integer value1, Integer value2) {
            addCriterion("tsc_id between", value1, value2, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tsc_id not between", value1, value2, "tscId");
            return (Criteria) this;
        }

        public Criteria andCtIdIsNull() {
            addCriterion("ct_id is null");
            return (Criteria) this;
        }

        public Criteria andCtIdIsNotNull() {
            addCriterion("ct_id is not null");
            return (Criteria) this;
        }

        public Criteria andCtIdEqualTo(Integer value) {
            addCriterion("ct_id =", value, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdNotEqualTo(Integer value) {
            addCriterion("ct_id <>", value, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdGreaterThan(Integer value) {
            addCriterion("ct_id >", value, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ct_id >=", value, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdLessThan(Integer value) {
            addCriterion("ct_id <", value, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdLessThanOrEqualTo(Integer value) {
            addCriterion("ct_id <=", value, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdIn(List<Integer> values) {
            addCriterion("ct_id in", values, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdNotIn(List<Integer> values) {
            addCriterion("ct_id not in", values, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdBetween(Integer value1, Integer value2) {
            addCriterion("ct_id between", value1, value2, "ctId");
            return (Criteria) this;
        }

        public Criteria andCtIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ct_id not between", value1, value2, "ctId");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeIsNull() {
            addCriterion("ep_start_time is null");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeIsNotNull() {
            addCriterion("ep_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("ep_start_time =", value, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ep_start_time <>", value, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ep_start_time >", value, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ep_start_time >=", value, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("ep_start_time <", value, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ep_start_time <=", value, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("ep_start_time in", values, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ep_start_time not in", values, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ep_start_time between", value1, value2, "epStartTime");
            return (Criteria) this;
        }

        public Criteria andEpStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ep_start_time not between", value1, value2, "epStartTime");
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