import React, { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { CategoryContent } from "../CategoryContent";
import cdata from "../../db/categories.json";
import styles from "../../styles/css/Category.module.css";

/* 아코디언 컴포넌트 이용 */
const CategoryBox = ({ onCategorySelect }) => {
  const [isAccordionOpen, setIsAccordionOpen] = useState(false);

  const toggleOpen = () => {
    setIsAccordionOpen(!isAccordionOpen);
  };

  const handleCategorySelect = (categoryId) => {
      //alert(categoryId+"category.js");
      onCategorySelect(categoryId); // 선택된 카테고리 ID를 부모 컴포넌트(Home)로 전달
  };

  return (
    <AnimatePresence>
      <motion.div layout className={styles.categoryWrapper}>
      <motion.div
        layout
        className={styles.categorybox}
        onClick={toggleOpen}
        initial={{ borderRadius: 5 }}
      >
        <motion.div layout className={styles.accordionTitle}>
          <motion.img
            layout
            className={styles.avatar}
            alt=""
            src="/img/category-menu.svg"
          />
          <motion.div layout className={styles.ctitleLabel}>
            Category
          </motion.div>
        </motion.div>
        {isAccordionOpen && (
          <motion.div layout className={styles.accordionContent}>
            {/* Add your accordion content here */}
            {cdata.categories.map((citem) => (
            <CategoryContent
                className={styles.categoryItem}
                text={citem.category_name}
                category_id={citem.category_id}
                onCategorySelect={handleCategorySelect}
            />
            ))}
          </motion.div>
        )}
      </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};
export default CategoryBox;