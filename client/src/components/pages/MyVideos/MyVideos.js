import { Link } from 'react-router-dom';
import VideoLink from '../main_page/main_page_components/VideoLink';

function MyVideos({ videos, currentUser }) {
    const userVideos = videos.filter(video => video.uploader === currentUser.username);
    console.log(currentUser.username); 

    return (
        <div className="video-list">
            {userVideos.map((video) => (
                <div key={video.id}>
                    <VideoLink {...video} />
                    <Link to={`/edit-video/${video.id}`}>
                        <button>Edit Video</button>
                    </Link>
                </div>
            ))}
            <Link to="/UploadVideosPage">
                <button>Upload New Video</button>
            </Link>
        </div>
    );
}

export default MyVideos;
