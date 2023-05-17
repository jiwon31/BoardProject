import Profile from "components/mypage/Profile";
import TabList from "components/mypage/TabList";

export default function Mypage() {
  return (
    <div className="flex py-10 max-w-6xl mx-auto">
      <TabList />
      <Profile />
    </div>
  );
}
