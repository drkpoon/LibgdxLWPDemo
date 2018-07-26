package com.mygdx.game.libgdxlwpdemo;

import android.content.Context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

class MyApplicationListener implements ApplicationListener {
    private static final float WORLD_LIMIT = 20f;
    private static final float WALL_HALF_THICKNESS = 1f;
    private static final float RADIUS = 1f;
    private static final int NO_OF_DISC = 20;
    private Context mContext;
    private float mWorldHalfWidth;
    private float mWorldHalfHeight;
    private Matrix4 mCameraCombined;
    private World mWorld;
    private Array<Body> mDisc;
    //    private Box2DDebugRenderer mBox2DDebugRenderer;
    private TextureRegion mTextureRegion;
    private SpriteBatch mSpriteBatch;

    MyApplicationListener(Context context) {
        mContext = context;
    }

    @Override
    public void create() {
        createProjectionMatrix();

        mTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("smile.png")));
        mSpriteBatch = new SpriteBatch();
        mWorld = new World(new Vector2(0, 0), false);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(mWorldHalfWidth, WALL_HALF_THICKNESS);
        createBody(BodyDef.BodyType.StaticBody, new Vector2(0, -mWorldHalfHeight - WALL_HALF_THICKNESS), polygonShape);
        createBody(BodyDef.BodyType.StaticBody, new Vector2(0, mWorldHalfHeight + WALL_HALF_THICKNESS), polygonShape);
        polygonShape.setAsBox(WALL_HALF_THICKNESS, mWorldHalfHeight);
        createBody(BodyDef.BodyType.StaticBody, new Vector2(mWorldHalfWidth + WALL_HALF_THICKNESS, 0), polygonShape);
        createBody(BodyDef.BodyType.StaticBody, new Vector2(-mWorldHalfWidth - WALL_HALF_THICKNESS, 0), polygonShape);
        polygonShape.dispose();

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(RADIUS);
        mDisc = new Array<>();
        for (int i = 0; i < NO_OF_DISC; i++) {
            mDisc.add(createBody(BodyDef.BodyType.DynamicBody, new Vector2(-1 + (i / (NO_OF_DISC / 2)) * 2.1f, -4 + 2.1f * (i % (NO_OF_DISC / 2))), circleShape));
            mDisc.get(i).applyForce(new Vector2(0, (float) (-10000f * Math.random())), new Vector2(0.5f * RADIUS, 0), true);
        }
        circleShape.dispose();

//        mBox2DDebugRenderer = new Box2DDebugRenderer();
//        gl.glLineWidth(5f);
    }

    private Body createBody(BodyDef.BodyType bodyType, Vector2 position, Shape shape) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        Body body = mWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1f;
        fixtureDef.density = 100f;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        return body;
    }

    private void createProjectionMatrix() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float scaleFactor = Math.min(screenWidth, screenHeight) / WORLD_LIMIT;
        mWorldHalfWidth = screenWidth / 2 / scaleFactor;
        mWorldHalfHeight = screenHeight / 2 / scaleFactor;

        OrthographicCamera camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.position.set(0, 0, 0);
        camera.rotate(0, 0, 1, Gdx.input.getRotation());
        camera.update();
        mCameraCombined = camera.combined.cpy().scale(scaleFactor, scaleFactor, 1);
    }

    @Override
    public void resize(int width, int height) {
        createProjectionMatrix();
    }

    @Override
    public void render() {
        mWorld.setGravity(new Vector2(-Gdx.input.getAccelerometerX(), -Gdx.input.getAccelerometerY()));
        mWorld.step(Gdx.graphics.getDeltaTime(), 10, 6);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //        mBox2DDebugRenderer.render(mWorld, mCameraCombined);
        mSpriteBatch.setProjectionMatrix(mCameraCombined);
        mSpriteBatch.begin();
        for (Body body : mDisc) {
            mSpriteBatch.draw(mTextureRegion, body.getPosition().x - RADIUS, body.getPosition().y - RADIUS,
                    RADIUS, RADIUS, 2 * RADIUS, 2 * RADIUS, 1, 1,
                    (float) (Math.toDegrees(body.getAngle()) - 90));
        }
        mSpriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
//        mBox2DDebugRenderer.dispose();
        if (mWorld != null) {
            mWorld.dispose();
        }
        if (mTextureRegion.getTexture() != null) {
            mTextureRegion.getTexture().dispose();
        }
        if (mSpriteBatch != null) {
            mSpriteBatch.dispose();
        }
    }
}
