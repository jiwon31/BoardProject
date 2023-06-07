import { useQuery } from "@tanstack/react-query";
import LikeApi from "api/like-api";
import useRecoilUser from "hooks/useRecoilUser";
import { Board } from "types/board";

export default function useLikedBoard(likeApi = new LikeApi()) {
  const { user } = useRecoilUser();

  const likedBoardsQuery = useQuery<Board[], Error>(
    ["liked-boards", { userId: user?.id }],
    likeApi.getUserLikedBoardList,
    { staleTime: 1000 * 60 * 5, enabled: !!user }
  );

  return { likedBoardsQuery };
}
