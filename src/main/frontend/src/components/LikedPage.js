import React, { useState } from "react";
import axios from "axios";
import styles from "../styles/css/ContentDetail.module.css";
import style2 from "../styles/css/Category.module.css";
import HeartList from "./HeartList";
import Loading from "./Loading";

const LikedPage = () => {
  const [loading, setLoading] = useState(false);

  if (loading)
    return (
      <div>
        <Loading />
      </div>
    );

  return (
    <div className={styles.divhome}>
      <div className={styles.divcategory}>
        <div className={style2.categoryWrapper}>
          <div className={style2.categorybox}>
            <div className={style2.accordionTitle}>
              <span className={style2.ctitleLabel}>♥찜</span>
            </div>
          </div>
        </div>
      </div>
      <div className={styles.divcontent}>
        <HeartList></HeartList>
      </div>
    </div>
  );
};

export default LikedPage;
