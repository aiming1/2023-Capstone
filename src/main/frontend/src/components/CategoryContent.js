import PropTypes from "prop-types";
import React from "react";
import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";
import { useHistory } from "react-router";
import styles from "../styles/css/Category.module.css";

export const CategoryContent = ({ text = "여성패션", category_id, onCategorySelect}) => {
    const navigate = useNavigate();
    const changeCategory = () => {
      onCategorySelect(category_id); // 선택한 카테고리를 부모 컴포넌트로 전달
      //alert(category_id);
      navigate(`/search?category=${category_id}`, {state: category_id});
      //window.location.reload();
    }

    return (
    <motion.div layout
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
    >
        <div className={styles.categoryContent}>
            <ul id={styles.leftToRight}>
                <li><input className={styles.contentBtn} type="submit" value={text} onClick={changeCategory} /></li>
            </ul>
        </div>
    </motion.div>
  );
};

CategoryContent.propTypes = {
  text: PropTypes.string,
};