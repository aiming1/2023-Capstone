import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "../styles/css/ItemList.module.css";
import { ListItem } from "./ListItem";
import { useParams } from "react-router";

//글자수 제한 함수
const truncate = (str, n) => {
  return str?.length > n ? str.substr(0, n - 1) + "..." : str;
};

const Search = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const params = useParams();
  const fetchData = async () => {
    try {
      // 요청이 시작 할 때에는 error 와 users 를 초기화하고
      setError(null);
      setData(null);
      // loading 상태를 true 로 바꿉니다.
      setLoading(true);
      const response = await axios.get(
        "/api/search/" + params.word + "/" + page
      );
      console.log(response.data);
      setData(response.data); // 데이터는 response.data 안에 들어있습니다.
    } catch (e) {
      setError(e);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, [page]);

  if (loading) return <div>로딩중..</div>;
  if (error) return <div>에러가 발생했습니다</div>;
  if (!data) return null;
  return (
    <div>
      <div className={styles.itemlistcontent}>
        {data.map((item) => (
          <ListItem
            className={styles.listItem}
            key={item.name}
            id={item.id}
            market={item.market}
            price={item.price}
            name={truncate(item.name, 10)}
            image={item.image}
            hearts={item.hearts}
          />
        ))}
      </div>
      <div className={styles.pageMove}>
        <button
          className={styles.pageBox}
          onClick={() => {
            setPage(page - 1);
          }}
        >
          이전
        </button>
        {page}
        <button
          className={styles.pageBox}
          onClick={() => {
            setPage(page + 1);
          }}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default Search;
