import { useMutation, useQuery } from "@tanstack/react-query";
import LikeApi from "api/like-api";
import useRecoilUser from "hooks/useRecoilUser";
import { GetUserLikedBoardList } from "types/like";

export default function useLikedBoard(likeApi = new LikeApi()) {
  const { user } = useRecoilUser();

  // 페이지 들어갔을 때 자동 조회
  const likedBoardsQuery = useQuery<GetUserLikedBoardList, Error>(
    ["liked-boards", { userId: user?.id }],
    () => likeApi.getUserLikedBoardList(),
    { staleTime: 1000 * 60 * 3, enabled: !!user }
  );

  // 더보기 버튼 눌렀을 때 추가 조회
  const getLikedBoardList = useMutation<GetUserLikedBoardList, Error, number>(
    (page) => likeApi.getUserLikedBoardList(page)
  );

  return { likedBoardsQuery, getLikedBoardList };
}
