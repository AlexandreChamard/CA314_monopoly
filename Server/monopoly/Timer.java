import java.util.concurrent.TimeUnit;

public class Timer {
    private long mStart = 0;
    private long mTime = 0;
    private boolean mRunning = false;

    @Override
    public String toString() {
        return getElapsedString(TimeUnit.SECONDS);
    }

    public long getElapsed() {
        update();
        return mTime;
    }


    public long getElapsed(TimeUnit unit) {
        long elapsed = -1;

        switch(unit) {
            case SECONDS:
                elapsed = getElapsed() / 1000;
                break;
            default:
                break;
        }
        return elapsed;
    }

    public String getElapsedString(TimeUnit unit) {
        String u;

        switch(unit) {
            case SECONDS:
                u = "s";
                break;
            default:
                u = "";
                break;
        }
        return String.format("%d%s", getElapsed(unit), u);
    }

    public void setTime(long t) {
        mTime = t * 1000;
    }

    public void start() {
        if (mTime > 0 && isRunning() == false) {
            mStart = System.currentTimeMillis();
            mRunning = true;
        }
    }

    public void stop() {
        update();
        mRunning = false;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public long getTimeLeft() {
        update();
        return mTime / 1000;
    }

    private void update() {
        if (isRunning()) {
            long t = System.currentTimeMillis();
            mTime -= t - mStart;
            mStart = t;
            if (mTime <= 0) {
                mTime = 0;
                mRunning = false;
            }
        }
    }

    public boolean isEnd() {
        update();
        return mTime == 0;
    }

    /** test of Timer */
    public static void main(String[] args) throws InterruptedException {
        Timer t = new Timer();
        boolean a = false;

        t.setTime(10);

        t.start();
        while (t.isEnd() == false) {
            Thread.sleep(100);
            if (a == false && t.getTimeLeft() == 5) {
                a = true;
                t.stop();
                Thread.sleep(5000);
                t.start();
            }
            System.out.println(t);
        }
    }

}
