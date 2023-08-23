import React, { useState } from "react";
import styled from "styled-components";

/*
새로추가
-total: 총 게시물 수
-setPage() 함수를 호출하여 page 상태 변경
  => <ItemList/> 컴포넌트는 새로운 페이지 번호에 해당하는 게시물 범위를 계산하여 다시 화면을 렌터링

*/

function Pagination({ total, limit, page, setPage }) {
  const numPages = Math.ceil(total / limit); //필요한 페이지의 개수
  /*페이지 40개씩- 추가 수정한 부분 */
  const [currPage, setCurrPage] = useState(page);
  let firstNum = currPage - (currPage % 5) + 1;
  let lastNum = currPage - (currPage % 5) + 5;

  return (
    <Nav>
      <Button
        onClick={() => {
          setPage(page - 1);
          setCurrPage(page - 2);
        }}
        disabled={page === 1}
      >
        &lt;
      </Button>
      <Button
        onClick={() => setPage(firstNum)}
        aria-current={page === firstNum ? "page" : null}
      >
        {firstNum}
      </Button>
      {Array(4)
        .fill()
        .map((_, i) => {
          if (i <= 2) {
            return (
              <Button
                border="true"
                key={i + 1}
                onClick={() => {
                  setPage(firstNum + 1 + i);
                }}
                aria-current={page === firstNum + 1 + i ? "page" : null}
              >
                {firstNum + 1 + i}
              </Button>
            );
          } else if (i >= 3) {
            return (
              <Button
                border="true"
                key={i + 1}
                onClick={() => setPage(lastNum)}
                aria-current={page === lastNum ? "page" : null}
              >
                {lastNum}
              </Button>
            );
          }
        })}
      <Button
        onClick={() => {
          setPage(page + 1);
          setCurrPage(page);
        }}
        disabled={page === numPages}
      >
        &gt;
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
    visibility: hidden;
    /*background: #dbdbdb;
    cursor: revert;
    transform: revert;*/

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