import { useMutation, useQuery } from "@tanstack/react-query";
import UserApi from "api/user-api";
import useRecoilUser from "../useRecoilUser";
import { GetMyBoardListResponse } from "types/user";

export default function useMyBoard(userApi = new UserApi()) {
  const { user } = useRecoilUser();

  // 페이지 들어갔을 때 자동 조회
  const myBoardsQuery = useQuery<GetMyBoardListResponse, Error>(
    ["boards", { userId: user?.id }],
    () => userApi.getMyBoardList(),
    { staleTime: 1000 * 60 * 3, enabled: !!user }
  );

  // 더보기 버튼 눌렀을 때 추가 조회
  const getMyBoardList = useMutation<GetMyBoardListResponse, Error, number>(
    (page) => userApi.getMyBoardList(page)
  );

  return { myBoardsQuery, getMyBoardList };
}
