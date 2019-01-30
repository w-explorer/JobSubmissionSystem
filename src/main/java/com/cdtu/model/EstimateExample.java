package com.cdtu.model;

import java.util.ArrayList;
import java.util.List;

public class EstimateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EstimateExample() {
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

        public Criteria andEIdIsNull() {
            addCriterion("e_id is null");
            return (Criteria) this;
        }

        public Criteria andEIdIsNotNull() {
            addCriterion("e_id is not null");
            return (Criteria) this;
        }

        public Criteria andEIdEqualTo(String value) {
            addCriterion("e_id =", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotEqualTo(String value) {
            addCriterion("e_id <>", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdGreaterThan(String value) {
            addCriterion("e_id >", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdGreaterThanOrEqualTo(String value) {
            addCriterion("e_id >=", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdLessThan(String value) {
            addCriterion("e_id <", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdLessThanOrEqualTo(String value) {
            addCriterion("e_id <=", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdLike(String value) {
            addCriterion("e_id like", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotLike(String value) {
            addCriterion("e_id not like", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdIn(List<String> values) {
            addCriterion("e_id in", values, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotIn(List<String> values) {
            addCriterion("e_id not in", values, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdBetween(String value1, String value2) {
            addCriterion("e_id between", value1, value2, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotBetween(String value1, String value2) {
            addCriterion("e_id not between", value1, value2, "eId");
            return (Criteria) this;
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

        public Criteria andESpeedIsNull() {
            addCriterion("e_speed is null");
            return (Criteria) this;
        }

        public Criteria andESpeedIsNotNull() {
            addCriterion("e_speed is not null");
            return (Criteria) this;
        }

        public Criteria andESpeedEqualTo(String value) {
            addCriterion("e_speed =", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedNotEqualTo(String value) {
            addCriterion("e_speed <>", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedGreaterThan(String value) {
            addCriterion("e_speed >", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedGreaterThanOrEqualTo(String value) {
            addCriterion("e_speed >=", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedLessThan(String value) {
            addCriterion("e_speed <", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedLessThanOrEqualTo(String value) {
            addCriterion("e_speed <=", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedLike(String value) {
            addCriterion("e_speed like", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedNotLike(String value) {
            addCriterion("e_speed not like", value, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedIn(List<String> values) {
            addCriterion("e_speed in", values, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedNotIn(List<String> values) {
            addCriterion("e_speed not in", values, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedBetween(String value1, String value2) {
            addCriterion("e_speed between", value1, value2, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andESpeedNotBetween(String value1, String value2) {
            addCriterion("e_speed not between", value1, value2, "eSpeed");
            return (Criteria) this;
        }

        public Criteria andEFileIsNull() {
            addCriterion("e_file is null");
            return (Criteria) this;
        }

        public Criteria andEFileIsNotNull() {
            addCriterion("e_file is not null");
            return (Criteria) this;
        }

        public Criteria andEFileEqualTo(String value) {
            addCriterion("e_file =", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileNotEqualTo(String value) {
            addCriterion("e_file <>", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileGreaterThan(String value) {
            addCriterion("e_file >", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileGreaterThanOrEqualTo(String value) {
            addCriterion("e_file >=", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileLessThan(String value) {
            addCriterion("e_file <", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileLessThanOrEqualTo(String value) {
            addCriterion("e_file <=", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileLike(String value) {
            addCriterion("e_file like", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileNotLike(String value) {
            addCriterion("e_file not like", value, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileIn(List<String> values) {
            addCriterion("e_file in", values, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileNotIn(List<String> values) {
            addCriterion("e_file not in", values, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileBetween(String value1, String value2) {
            addCriterion("e_file between", value1, value2, "eFile");
            return (Criteria) this;
        }

        public Criteria andEFileNotBetween(String value1, String value2) {
            addCriterion("e_file not between", value1, value2, "eFile");
            return (Criteria) this;
        }

        public Criteria andESuggestIsNull() {
            addCriterion("e_suggest is null");
            return (Criteria) this;
        }

        public Criteria andESuggestIsNotNull() {
            addCriterion("e_suggest is not null");
            return (Criteria) this;
        }

        public Criteria andESuggestEqualTo(String value) {
            addCriterion("e_suggest =", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestNotEqualTo(String value) {
            addCriterion("e_suggest <>", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestGreaterThan(String value) {
            addCriterion("e_suggest >", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestGreaterThanOrEqualTo(String value) {
            addCriterion("e_suggest >=", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestLessThan(String value) {
            addCriterion("e_suggest <", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestLessThanOrEqualTo(String value) {
            addCriterion("e_suggest <=", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestLike(String value) {
            addCriterion("e_suggest like", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestNotLike(String value) {
            addCriterion("e_suggest not like", value, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestIn(List<String> values) {
            addCriterion("e_suggest in", values, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestNotIn(List<String> values) {
            addCriterion("e_suggest not in", values, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestBetween(String value1, String value2) {
            addCriterion("e_suggest between", value1, value2, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andESuggestNotBetween(String value1, String value2) {
            addCriterion("e_suggest not between", value1, value2, "eSuggest");
            return (Criteria) this;
        }

        public Criteria andEDifficultIsNull() {
            addCriterion("e_difficult is null");
            return (Criteria) this;
        }

        public Criteria andEDifficultIsNotNull() {
            addCriterion("e_difficult is not null");
            return (Criteria) this;
        }

        public Criteria andEDifficultEqualTo(String value) {
            addCriterion("e_difficult =", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultNotEqualTo(String value) {
            addCriterion("e_difficult <>", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultGreaterThan(String value) {
            addCriterion("e_difficult >", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultGreaterThanOrEqualTo(String value) {
            addCriterion("e_difficult >=", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultLessThan(String value) {
            addCriterion("e_difficult <", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultLessThanOrEqualTo(String value) {
            addCriterion("e_difficult <=", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultLike(String value) {
            addCriterion("e_difficult like", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultNotLike(String value) {
            addCriterion("e_difficult not like", value, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultIn(List<String> values) {
            addCriterion("e_difficult in", values, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultNotIn(List<String> values) {
            addCriterion("e_difficult not in", values, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultBetween(String value1, String value2) {
            addCriterion("e_difficult between", value1, value2, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andEDifficultNotBetween(String value1, String value2) {
            addCriterion("e_difficult not between", value1, value2, "eDifficult");
            return (Criteria) this;
        }

        public Criteria andSEStateIsNull() {
            addCriterion("s_e_state is null");
            return (Criteria) this;
        }

        public Criteria andSEStateIsNotNull() {
            addCriterion("s_e_state is not null");
            return (Criteria) this;
        }

        public Criteria andSEStateEqualTo(Boolean value) {
            addCriterion("s_e_state =", value, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateNotEqualTo(Boolean value) {
            addCriterion("s_e_state <>", value, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateGreaterThan(Boolean value) {
            addCriterion("s_e_state >", value, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("s_e_state >=", value, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateLessThan(Boolean value) {
            addCriterion("s_e_state <", value, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateLessThanOrEqualTo(Boolean value) {
            addCriterion("s_e_state <=", value, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateIn(List<Boolean> values) {
            addCriterion("s_e_state in", values, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateNotIn(List<Boolean> values) {
            addCriterion("s_e_state not in", values, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateBetween(Boolean value1, Boolean value2) {
            addCriterion("s_e_state between", value1, value2, "sEState");
            return (Criteria) this;
        }

        public Criteria andSEStateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("s_e_state not between", value1, value2, "sEState");
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