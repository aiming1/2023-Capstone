import React, { useState, useEffect } from "react";
import styled from "styled-components";

export const Pagination=({
  postLength, // 아이템 배열의 길이
  oneViewNumber, // 한 페이지의 보여줄 Number
  page, // 현재페이지
  setPage,
  inMaxPageListNumber, // 페이지 그룹 개수
}) => {
  const [numPages, setNumPages] = useState(
    Math.ceil(postLength / oneViewNumber)
  );

  const middleList = Array(numPages)
    .fill()
    .map((v, i) => i + 1);

  const [start, setStart] = useState(0);
  const [end, setEnd] = useState(inMaxPageListNumber);

  const [resultList, setResultList] = useState(middleList.slice(start, end));

  useEffect(() => {

    var fpage = page;
    var fInMaxPageListNumber = inMaxPageListNumber;

    var start = 0;
    var end = inMaxPageListNumber;
    var pp = 0; //현재 페이지

    while (fpage > fInMaxPageListNumber) {
      fpage = fpage - fInMaxPageListNumber;
      pp++;
      if (fpage < fInMaxPageListNumber) {
        break;
      }
    }

    start = inMaxPageListNumber * pp + 1;
    end = inMaxPageListNumber * (pp + 1);

    setStart(start);
    setEnd(end);

    setResultList(middleList.slice(start - 1, end));
  }, [page]);

  return (
    <Nav>
        <Button
          onClick={() => {
            setPage(1);
          }}
          disabled={page === 1}
        >
          <span>&lt;&lt;</span>
        </Button>
      <Button
        onClick={() => {
          setPage(page - 1);
        }}
        disabled={page === 1}
      >
        <span>&lt;</span>
      </Button>
      {resultList.map((i) => (
        <span key={i} >
          <Button
            key={i}
            onClick={() => {
              setPage(i);
            }}
            aria-current={page === i ? "page" : null}
          >
            {i}
          </Button>
        </span>
      ))}
        <Button
          onClick={() => {
            setPage(page + 1);
          }}
          disabled={page === numPages}
        >
          <span>&gt;</span>
        </Button>
        <Button
          onClick={() => {
            setPage(numPages);
          }}
          disabled={page === numPages}
        >
          <span>&gt;&gt;</span>
        </Button>
    </Nav>
  );
}

const Nav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  margin: 16px;
`;

const Button = styled.button`
  border-radius: 3px;
  padding: 8px;
  margin: 0;
  background: white;
  color: #191919;
  font-size: 1rem;
  border: 0px;

  &:hover {
    text-decoration: underline;
    cursor: pointer;
    transform: translateY(-2px);
  }

  &[disabled] {
    /*visibility: hidden;
    background: #dbdbdb;*/

    color: #dbdbdb;
    cursor: revert;
    transform: revert;
  }

  &[aria-current] {
    background: #5d1fc2;
    color: white;
    font-weight: bold;
    cursor: revert;
    transform: revert;
  }
`;

export default Pagination;