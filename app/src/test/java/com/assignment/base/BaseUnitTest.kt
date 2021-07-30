package com.assignment.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.BuildConfig
import com.assignment.NYApp
import com.assignment.di.module.application.RetrofitModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Retrofit
import java.io.File

/**
 * Base class for all types of unit test and instrumentation tests
 * All the  initial mocks required for enabling unit testing are done in this class.
 * The class will be using Mockito and PowerMock for mocking purposes
 */
@ExperimentalCoroutinesApi
@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(
    Log::class,
    Handler::class,
    Looper::class,
    TextUtils::class,
    BuildConfig::class,
    NYApp::class
)
abstract class BaseUnitTest {

    lateinit var context: Context
    lateinit var sharedPreferences: SharedPreferences

    /**
     * For MockWebServer instance
     */
    private lateinit var mMockServerInstance: MockWebServer

    lateinit var retrofit: Retrofit

    val dispatcher = TestCoroutineDispatcher()

    /**
     * Default, let server be shut down
     */
    private var mShouldStart = false

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    val testCoroutineScope = TestCoroutineScope(dispatcher)

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        // Initializes the mock environment
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        startMockServer(true)

        // Mocks the generic android dependencies such as Context, Looper, etc.
        mockAndroidDependencies()

        // Mocks android logs
        mockLogs()

        // Mocks shared preferences
        mockSharedPreference()

        // Initializes the retrofit dependencies
        initDependencies()
    }

    /**
     * This function will mock all the android Log related dependencies
     */
    private fun mockLogs() {
        PowerMockito.mockStatic(Log::class.java)
        val logAnswer = Answer<Void> { invocation ->
            val tag = invocation.arguments[0] as String
            val msg = invocation.arguments[1] as String
            println(invocation.method.name.toUpperCase() + "/[" + tag + "] " + msg)
            null
        }
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "d",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "i",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "w",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "e",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "wtf",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )

        PowerMockito.doAnswer { invocation ->
            val s = invocation.arguments[0] as String
            s.isEmpty()
        }.`when`(TextUtils::class.java, "isEmpty", ArgumentMatchers.anyString())

    }

    /**
     * This function will mock all the SharedPreference related dependencies
     */
    private fun mockSharedPreference() {
        PowerMockito.`when`(
            sharedPreferences.getString(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn("")
        PowerMockito.`when`(
            sharedPreferences.getLong(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyLong()
            )
        ).thenReturn(0L)
        PowerMockito.`when`(
            sharedPreferences.getInt(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(0)
        PowerMockito.`when`(
            sharedPreferences.getBoolean(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyBoolean()
            )
        ).thenReturn(false)
        PowerMockito.`when`(
            sharedPreferences.getFloat(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyFloat()
            )
        ).thenReturn(0f)
        PowerMockito.`when`(
            context.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(sharedPreferences)
        PowerMockito.`when`(context.packageName).thenReturn("com.assignment")
    }

    /**
     * This method initializes the retrofit module
     */
    private fun initDependencies() {
        val retrofitModule = RetrofitModule()
        val createLoggingInterceptor = retrofitModule.createLoggingInterceptor()
        val httpClient = retrofitModule.createHttpClient(createLoggingInterceptor)
        val gsonConverterFactory = retrofitModule.gsonConverterFactory(Gson())
        val serverUrl = getMockWebServerUrl()
        retrofit = retrofitModule.provideRetrofit(serverUrl, httpClient, gsonConverterFactory)
    }

    private fun mockAndroidDependencies() {
        context = PowerMockito.mock(Context::class.java)
        sharedPreferences = PowerMockito.mock(SharedPreferences::class.java)
        PowerMockito.mockStatic(Looper::class.java)
        PowerMockito.mockStatic(Handler::class.java)
        PowerMockito.mockStatic(TextUtils::class.java)
        PowerMockito.`when`(Looper.getMainLooper()).thenReturn(null)
        PowerMockito.whenNew(Handler::class.java).withAnyArguments().thenReturn(null)
    }

    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    /**
     * Reads input file and converts to json
     */
    private fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    /**
     * Start Mockwebserver
     */
    private fun startMockServer(shouldStart: Boolean) {
        if (shouldStart) {
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    /**
     * Stop Mockwebserver
     */
    private fun stopMockServer() {
        if (mShouldStart) {
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown() {
        //Stop Mock server
        stopMockServer()
        Dispatchers.resetMain()
    }
}