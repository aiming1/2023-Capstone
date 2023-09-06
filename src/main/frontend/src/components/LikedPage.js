import React, { useState } from "react";
import axios from "axios";
import styles from "../styles/css/ContentDetail.module.css";
import style2 from "../styles/css/Category.module.css";
import HeartList from "./HeartList";

const LikedPage = () => {
  const [deleteAll, setDeleteAll] = useState(false);

  //찜 해제
  const deleteHeartAll = async () => {
    axios
      .get("/api/list/delete")
      .then(function (response) {
        console.log("삭제 성공", response);
        setDeleteAll(true);
        alert("찜목록을 비웠습니다.");
      })
      .catch(function (error) {
        // 오류발생시 실행
        console.log("실패", error);
      })
      .then(function () {
        // 항상 실행
        console.log("데이터 요청 완료");
      });
  };

  return (
    <div className={styles.divhome}>
      <div className={styles.divcategory}>
        <div className={style2.categorybox}>
          <span className={style2.ctitleLabel}>♥찜</span>
          <span className={style2.cdelete} onClick={deleteHeartAll}>
            전체 삭제
          </span>
        </div>
      </div>
      <div className={styles.divcontent}>
        <HeartList deleteAll={deleteAll}></HeartList>
      </div>
    </div>
  );
};

export default LikedPage;
