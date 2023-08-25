import PropTypes from "prop-types";
import React, { useState } from "react";
import { useNavigate } from "react-router";
import HeartButtonMain from "./HeartButtonMain";
import styles from "../styles/css/ItemList.module.css";
import axios from "axios";

export const ListItem = ({
  id = 0,
  name = "상품명",
  market = "당근",
  price = "0",
  image = "/img/빈 이미지.svg",
  hearts = 0,

  isHeart, //하트 유지
  data,
  heartListId,
}) => {
  const productId = id;
  const productMarket = market[0];

  price = price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  //가격에 , 추가
  const renderLogo = () => {
    if (market === "CARROT") {
      //당근마켓
      return (
        <img
          className={styles.itemstorelogo}
          alt=""
          src="/img/당근마켓 아이콘 1.png"
        />
      );
    } else if (market === "BUNJANG") {
      //번개장터
      return (
        <img
          className={styles.itemstorelogo}
          alt=""
          src="/img/번개장터 아이콘 1.png"
        />
      );
    } else if (market === "JOONGGONARA") {
      //중고나라
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
      state: {
        id: id,
        market: market,
        image: image,
        price: price,
      },
    });
  };

  //찜하기
  const [heart, setHeart] = useState(isHeart);
  const addHeart = async () => {
    const productData = {
      id: productId,
      name: data?.name,
      image: data?.image,
      price: data?.price,
      market: productMarket,
      seller: data?.seller,
      updatedate: data?.updatedate,
      hearts: data?.hearts,
      details: data?.details,
      category: data?.category,
      producturl: data?.producturl,
    };
    axios
      .get(`/api/product/${productId}/${productMarket}/heart/add`, productData)
      .then(function (response) {
        console.log("추가 성공", response);
        setHeart(!heart);
        alert("찜 성공");
        // response
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

  //찜 해제
  const deleteHeart = async () => {
    axios
      .get(`/api/product/${productId}/${productMarket}/heart/delete`)
      .then(function (response) {
        console.log("삭제 성공", response);
        setHeart(!heart);
        alert("찜 해제");
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
    <div className={styles.listItem}>
      <div className={styles.imgContainer}>
        <img
          className={styles.itemimg}
          alt=""
          src={image}
          onClick={handleItemClick}
        />
        {heart ? (
          <HeartButtonMain
            heart={heart}
            onClick={deleteHeart}
          ></HeartButtonMain>
        ) : (
          <HeartButtonMain heart={heart} onClick={addHeart}></HeartButtonMain>
        )}

        {/*<HeartButtonMain heart={heart} onClick={handleHeartClick} />*/}
        {/* <button className={styles.heartButton} onClick={handleHeartClick}>
                <img className={styles.heartIcon} src="/img/heart_empty.png" alt="하트" />
            </button>*/}
      </div>
      <div className={styles.iteminfo}>
        {renderLogo()}
        <div className={styles.itemname}>{name}</div>
        <div className={styles.itempick}>찜 {hearts}</div>
        <div className={styles.itemprice}>{price} 원</div>
      </div>
    </div>
  );
};

ListItem.propTypes = {
  text: PropTypes.string,
  market: PropTypes.string,
};
