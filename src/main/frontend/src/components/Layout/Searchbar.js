import styles from "../../styles/css/Header.module.css";
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function Searchbar() {
  {}

  const onSubmitSearch = (e) => {
    if(e.key === "Enter"){
      window.location.href = "/search/" + word;
      //alert(word);
    }
  }

  const [word, setWord] = useState("");

  const onSubmit = async () => {
    window.location.href = "/search/" + word;
  };

  return (
    <div className={styles.searchbarContainer}>
      <div className={styles.groupItem}>
        <div className={styles.inputWrapper}>
          <button
            className={styles.searchAltIcon}
            type="submit"
            onClick={() => {onSubmit();}}
          >
            <img alt="" src="/img/search-alt.svg" />
          </button>

          <input
            className={styles.div16}
            name="keyword"
            type="text"
            placeholder="상품명 입력"
            value={word}
            autocomplete="off"
            onChange={(e) => {
              setWord(e.target.value);
              console.log(word);
            }}
            onKeyPress={onSubmitSearch}
          />
        </div>
      </div>
    </div>
  );
}
