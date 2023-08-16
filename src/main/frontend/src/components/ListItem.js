import PropTypes from "prop-types";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import HeartButtonMain from "./HeartButtonMain";
import styles from "../styles/css/ItemList.module.css";

export const ListItem = ({
  id = 0,
  title = "상품명",
  store = "당근",
  price = "0",
  src = "/img/빈 이미지.svg",
  heartCnt = 0,
}) => {
  const renderLogo = () => {
    if (store === "CARROT") { //당근마켓
      return (
        <img
          className={styles.itemstorelogo}
          alt=""
          src="/img/당근마켓 아이콘 1.png"
        />
      );
    } else if (store === "BUNJANG") { //번개장터
      return (
        <img
          className={styles.itemstorelogo}
          alt=""
          src="/img/번개장터 아이콘 1.png"
        />
      );
    } else if (store === "JOONGGONARA") { //중고나라
      return (
        <img
          className={styles.itemstorelogo}
          alt=""
          src="/img/중고나라 아이콘 1.png"
        />
      );
    }
    return null;
  };

  const navigate = useNavigate();
  const handleItemClick = () => {
    //window.location.href = `/product/${id}`;
    navigate(`/product/${id}`, {
        state : {
          id: id,
          store: store
        }
    });
  };


  const handleHeartClick = () => {
    setLike(!like);
  };

  const [like, setLike] = useState(false);
  {
    /* const [like, setLike] = useState(false)

    useEffect(async () => {
      const fetchData = async () => {
        const res = await axios.get(...)
        if (res.data.type === 'liked') setLike(true)
      }
      fetchData()
    }, []); */
  }

  return (
    <div className={styles.listItem}>
      <div className={styles.imgContainer}>
        <img
          className={styles.itemimg}
          alt=""
          src={src}
          onClick={handleItemClick}
        />

        <HeartButtonMain like={like} onClick={handleHeartClick} />
        {/* <button className={styles.heartButton} onClick={handleHeartClick}>
                <img className={styles.heartIcon} src="/img/heart_empty.png" alt="하트" />
            </button>*/}
      </div>
      <div className={styles.iteminfo}>
        {renderLogo()}
        <div className={styles.itemname}>{title}</div>
        <div className={styles.itempick}>찜 {heartCnt}</div>
        <div className={styles.itemprice}>{price} 원</div>
      </div>
    </div>
  );
};

ListItem.propTypes = {
  text: PropTypes.string,
  store: PropTypes.string,
};
