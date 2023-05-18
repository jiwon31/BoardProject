import MyBoards from "components/mypage/MyBoards";
import Profile from "components/mypage/Profile";
import TabList from "components/mypage/TabList";
import { useParams } from "react-router-dom";

export default function Mypage() {
  const { tab } = useParams();

  return (
    <div className="flex py-10 max-w-6xl mx-auto">
      <TabList tab={tab!} />
      {tab === "profile" ? <Profile /> : <MyBoards />}
    </div>
  );
}
