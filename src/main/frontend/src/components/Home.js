import React, { useState } from "react";
import Category from "./Layout/Category";
import ItemList from "./Layout/Itemlist";
import styles from "../styles/css/ContentDetail.module.css";

const Home = () => {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [categoryName, setCategoryName] = useState(null);

  const handleCategorySelect = (categoryId) => {
    //alert(categoryId+"home.js");
    setSelectedCategory(categoryId); // 선택된 카테고리 ID를 Home 컴포넌트로 전달
    printSelectedCategory(categoryId);
  };

  const printSelectedCategory = (categoryId) => {
      switch (categoryId) {
        case 1:
          setCategoryName("여성의류");
          break;
        case 2:
          setCategoryName("남성의류");
          break;
        case 3:
          setCategoryName("화장품 / 미용");
          break;
        case 4:
          setCategoryName("가구 / 인테리어");
          break;
        case 5:
          setCategoryName("문구류");
          break;
        case 6:
          setCategoryName("식품");
          break;
        case 7:
          setCategoryName("출산 / 유아동");
          break;
        case 8:
          setCategoryName("반려동물용품");
          break;
        case 9:
          setCategoryName("생활(주방/가전)");
          break;
        case 10:
          setCategoryName("디지털 / 컴퓨터");
          break;
        case 11:
          setCategoryName("스포츠 / 레저");
          break;
        default:
          // default code block (optional)
          break;
      }
  };

  return (
    <div className={styles.divhome}>
      <div className={styles.divcategory}>
        <Category onCategorySelect={handleCategorySelect} ></Category>
        {selectedCategory !== null && (
        <div className={styles.divcategorylabel}>
          <p style={{ marginBottom: '0px' }}><i class="fas fa-home" style={{ fontSize: '13px', margin: '0 5px' }}></i>
          홈 > {categoryName}</p>
        </div>)}
      </div>
      <div className={styles.divcontent}>
        <ItemList selectedCategoryId={selectedCategory} />
      </div>
    </div>
  );
};
export default Home;