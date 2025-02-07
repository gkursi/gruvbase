package xyz.qweru.api.animation;

/**
 * credit to alien client
 */
public class Animation {

    private long start;
    public long length;

    public Animation(final long ms) {
        this.length = ms;
        this.reset();
    }

    public void reset() {
        this.start = System.currentTimeMillis();
    }

    public boolean isEnd() {
        return this.getTime() >= this.length;
    }

    protected long getTime() {
        return System.currentTimeMillis() - this.start;
    }

    public void setLength(final long length) {
        this.length = length;
    }

    /**
     * @return The progress between 0 and 1 (0 - start, 1 - end)
     */
    public double getProgress() {
        return this.isEnd() ? 1.0 : (this.getTime() / (double) this.length);
    }

    public double getFadeIn() {
        return Math.tanh(this.getTime() / (double) this.length * 3.0);
    }

    public double getFadeOut() {
        return 1.0 - Math.tanh(this.getTime() / (double) this.length * 3.0);
    }

    public double getEpsEzFadeIn() {
        return 1.0 - Math.sin(1.5707963267948966 * this.getProgress()) * Math.sin(2.5132741228718345 * this.getProgress());
    }

    public double getEpsEzFadeOut() {
        return Math.sin(1.5707963267948966 * this.getProgress()) * Math.sin(2.5132741228718345 * this.getProgress());
    }

    public double easeOutQuad() {
        return 1.0 - (1.0 - this.getProgress()) * (1.0 - this.getProgress());
    }

    public double easeOutQuint() {
        return 1.0 - (1.0 - this.getProgress()) * (1.0 - this.getProgress()) * (1.0 - this.getProgress()) * (1.0 - this.getProgress());
    }

    public double easeCubicIn() {
        double factor = getProgress();
        return Math.pow(factor, 3);
    }

    public double easeCubicOut() {
        double factor = getProgress();
        return 1 - Math.pow(1 - factor, 3);
    }

    public double easeCubicInOut() {
        double factor = getProgress();
        return factor < 0.5 ? 4 * Math.pow(factor, 3) : 1 - Math.pow(-2 * factor + 2, 3) / 2;
    }

    public double easeInQuad() {
        return this.getProgress() * this.getProgress();
    }

    public static double easeOutBounce(double progress) {

        if (progress < 1 / 2.75) {
            return 7.5625 * progress * progress;
        } else if (progress < 2 / 2.75) {
            progress -= 1.5 / 2.75;
            return 7.5625 * progress * progress + 0.75;
        } else if (progress < 2.5 / 2.75) {
            progress -= 2.25 / 2.75;
            return 7.5625 * progress * progress + 0.9375;
        } else {
            progress -= 2.625 / 2.75;
            return 7.5625 * progress * progress + 0.984375;
        }
    }

    public double easeInQuintQuad() {
        return this.getProgress() * this.getProgress() * this.getProgress() * this.getProgress();
    }

    public double ease(Ease quad) {
        switch (quad) {
            case In -> {
                return easeInQuad();
            }
            case In2 -> {
                return getFadeIn();
            }
            case InQuint -> {
                return easeInQuintQuad();
            }
            case OutQuint -> {
                return easeOutQuint();
            }
            case Out -> {
                return easeOutQuad();
            }
            case Normal -> {
                return getProgress();
            }
            case Slow -> {
                double ease = Math.min(easeInQuad(), easeOutQuad());
                ease = Math.min(ease, Math.min(getFadeIn(), getProgress()));
                return ease;
            }
            case Fast -> {
                double ease = Math.max(easeInQuad(), easeOutQuad());
                ease = Math.max(ease, Math.max(getFadeIn(), getProgress()));
                return ease;
            }
            case Bounce -> {
                return easeOutBounce(getProgress());
            }
            case CUBIC_IN -> {
                return easeCubicIn();
            }
            case CUBIC_OUT -> {
                return easeCubicOut();
            }
            case CUBIC_IN_OUT -> {
                return easeCubicInOut();
            }
        }
        return easeOutQuad();
    }

    public enum Ease {
        Bounce,
        CUBIC_OUT,
        CUBIC_IN,
        CUBIC_IN_OUT,
        Fast,
        In,
        In2,
        InQuint,
        Normal,
        Out,
        OutQuint,
        Slow,
    }

    public static class StaticAnimation extends Animation {
        private final double value;

        public StaticAnimation(double value) {
            super(1000);
            this.value = value;
        }

        @Override
        public double getProgress() {
            return value;
        }

        @Override
        protected long getTime() {
            return (long) (this.length * value);
        }

        @Override
        public boolean isEnd() {
            return false;
        }
    }
}
