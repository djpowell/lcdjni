// lcdjni-c.cpp : Defines the exported functions for the DLL application.
//

#include "lcdjni-c.h"

#ifdef __cplusplus
extern "C" {
#endif

static JavaVM* jvm;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	jvm = vm;
	return JNI_VERSION_1_4;
}
	
/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdInit
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdInit
(JNIEnv *env, jclass clazz) {
	return lgLcdInit();
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdDeInit
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdDeInit
  (JNIEnv *env, jclass clazz) {
	  return lgLcdDeInit();
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdConnectEx
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdConnectEx
(JNIEnv *env, jclass clazz, jobject ctx) {
	return lgLcdConnectEx((lgLcdConnectContextEx*) env->GetDirectBufferAddress(ctx));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdDisconnect
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdDisconnect
(JNIEnv *env, jclass clazz, jint connection) {
	return lgLcdDisconnect(connection);
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdOpenByType
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdOpenByType
(JNIEnv *env, jclass clazz, jobject ctx) {
	return lgLcdOpenByType((lgLcdOpenByTypeContext*) env->GetDirectBufferAddress(ctx));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdClose
(JNIEnv *env, jclass clazz, jint device) {
	return lgLcdClose(device);
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdReadSoftButtons
 * Signature: (ILjava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdReadSoftButtons
(JNIEnv *env, jclass clazz, jint device, jobject buttons) {
	return lgLcdReadSoftButtons(device, (DWORD*) env->GetDirectBufferAddress(buttons));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    makeDword
 * Signature: ()Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_net_djpowell_lcdjni_Native_makeDword
(JNIEnv *env, jclass clazz) {
	DWORD* dw = (DWORD*)malloc(sizeof(DWORD));
	return env->NewDirectByteBuffer(dw, sizeof(DWORD));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    freeDword
 * Signature: (Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_net_djpowell_lcdjni_Native_freeDword
(JNIEnv *env, jclass clazz, jobject bb) {
	free(env->GetDirectBufferAddress(bb));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdUpdateBitmap
 * Signature: (ILjava/nio/ByteBuffer;I)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdUpdateBitmap
(JNIEnv *env, jclass clazz, jint device, jobject bmp, jint priority) {
	return lgLcdUpdateBitmap(device, (lgLcdBitmapHeader*) env->GetDirectBufferAddress(bmp), priority);
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    lgLcdSetAsLCDForegroundApp
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_net_djpowell_lcdjni_Native_lgLcdSetAsLCDForegroundApp
(JNIEnv *env, jclass clazz, jint device, jint foreground) {
	return lgLcdSetAsLCDForegroundApp(device, foreground);
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    makelgLcdBitmapHeader
 * Signature: (I)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_net_djpowell_lcdjni_Native_makelgLcdBitmapHeader
(JNIEnv *env, jclass clazz, jint format) {
	if (format == LGLCD_BMP_FORMAT_QVGAx32) {
		lgLcdBitmapQVGAx32 *bmp = (lgLcdBitmapQVGAx32*) malloc(sizeof(lgLcdBitmapQVGAx32));
		bmp->hdr.Format = format;
		return env->NewDirectByteBuffer(bmp, sizeof(lgLcdBitmapQVGAx32));
	} else if (format == LGLCD_BMP_FORMAT_160x43x1) {
		lgLcdBitmap160x43x1 *bmp = (lgLcdBitmap160x43x1*) malloc(sizeof(lgLcdBitmap160x43x1));
		bmp->hdr.Format = format;
		return env->NewDirectByteBuffer(bmp, sizeof(lgLcdBitmap160x43x1));
	} else {
		jclass ex = env->FindClass("java/lang/IllegalArgumentException");
		env->ThrowNew(ex, "Unknown format");
		return NULL;
	}
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    freelgLcdBitmapHeader
 * Signature: (Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_net_djpowell_lcdjni_Native_freelgLcdBitmapHeader
(JNIEnv *env, jclass clazz, jobject bb) {
	free(env->GetDirectBufferAddress(bb));
}

DWORD WINAPI myNotificationCallback(int connection, const PVOID pContext, DWORD notificationCode, DWORD notifyParm1, DWORD notifyParm2, DWORD notifyParm3, DWORD notifyParm4) {
	JNIEnv *env;
	jvm->AttachCurrentThread((void **)&env, NULL);
	jclass clazz = env->FindClass("net/djpowell/lcdjni/Native");
	jmethodID methodId = env->GetStaticMethodID(clazz, "masterOnNotification", "(IIIIIII)V");
	env->CallStaticVoidMethod(clazz, methodId, connection, (jint)pContext, notificationCode, notifyParm1, notifyParm2, notifyParm3, notifyParm4);
	jvm->DetachCurrentThread();
	return 0;
}

DWORD WINAPI myConfigureCallback(int connection, const PVOID pContext) {
	JNIEnv *env;
	jvm->AttachCurrentThread((void **)&env, NULL);
	jclass clazz = env->FindClass("net/djpowell/lcdjni/Native");
	jmethodID methodId = env->GetStaticMethodID(clazz, "masterOnConfigure", "(II)V");
	env->CallStaticVoidMethod(clazz, methodId, connection, (jint)pContext);
	jvm->DetachCurrentThread();
	return 0;
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    makelgLcdConnectContextEx
 * Signature: (Ljava/lang/String;ZIIZ)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_net_djpowell_lcdjni_Native_makelgLcdConnectContextEx
(JNIEnv *env, jclass clazz, jstring appFriendlyName, jboolean isAutoStartable, jint appletCapabilitiesSupported, jint context, jboolean hasConfig) {
	lgLcdConnectContextEx *ctx = (lgLcdConnectContextEx*) malloc(sizeof(lgLcdConnectContextEx));
	const jchar* strPointer = env->GetStringChars(appFriendlyName, NULL);
	jsize strLen = env->GetStringLength(appFriendlyName);
	LPWSTR strCopy = (LPWSTR)malloc(sizeof(wchar_t) * (1 + strLen));
	wcscpy(strCopy, (LPCWSTR)strPointer);
	env->ReleaseStringChars(appFriendlyName, strPointer);
	ctx->appFriendlyName = strCopy;
	ctx->dwAppletCapabilitiesSupported = appletCapabilitiesSupported;
	ctx->dwReserved1 = 0;
	ctx->isAutostartable = isAutoStartable;
	ctx->isPersistent = 0;
	ctx->onConfigure.configCallback = hasConfig ? myConfigureCallback : NULL;
	ctx->onConfigure.configContext = (PVOID)context;
	ctx->onNotify.notificationCallback = myNotificationCallback;
	ctx->onNotify.notifyContext = (PVOID)context;
	return env->NewDirectByteBuffer(ctx, sizeof(lgLcdConnectContextEx));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    freelgLcdConnectContextEx
 * Signature: (Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_net_djpowell_lcdjni_Native_freelgLcdConnectContextEx
(JNIEnv *env, jclass clazz, jobject bb) {
	lgLcdConnectContextEx *ctx = (lgLcdConnectContextEx*)env->GetDirectBufferAddress(bb);
	free((LPWSTR)ctx->appFriendlyName);
	free(ctx);
}


DWORD WINAPI mySoftButtonsCallback(int device, DWORD buttons, const PVOID pContext) {
	JNIEnv *env;
	jvm->AttachCurrentThread((void **)&env, NULL);
	jclass clazz = env->FindClass("net/djpowell/lcdjni/Native");
	jmethodID methodId = env->GetStaticMethodID(clazz, "masterOnKeyChange", "(III)V");
	env->CallStaticVoidMethod(clazz, methodId, device, buttons, (jint)pContext);
	jvm->DetachCurrentThread();
	return 0;
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    makelgLcdOpenByTypeContext
 * Signature: (III)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_net_djpowell_lcdjni_Native_makelgLcdOpenByTypeContext
(JNIEnv *env, jclass clazz, jint connection, jint deviceType, jint context) {
	lgLcdOpenByTypeContext *ctx = (lgLcdOpenByTypeContext*) malloc(sizeof(lgLcdOpenByTypeContext));
	ctx->connection = connection;
	ctx->deviceType = deviceType;
	ctx->onSoftbuttonsChanged.softbuttonsChangedCallback = mySoftButtonsCallback;
	ctx->onSoftbuttonsChanged.softbuttonsChangedContext = (PVOID)context;
	return env->NewDirectByteBuffer(ctx, sizeof(lgLcdOpenByTypeContext));
}

/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    freelgLcdOpenByTypeContext
 * Signature: (Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_net_djpowell_lcdjni_Native_freelgLcdOpenByTypeContext
(JNIEnv *env, jclass clazz, jobject bb) {
	lgLcdOpenByTypeContext *ctx = (lgLcdOpenByTypeContext*)env->GetDirectBufferAddress(bb);
	free(ctx);
}


/*
 * Class:     net_djpowell_lcdjni_Native
 * Method:    getConstants
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_net_djpowell_lcdjni_Native_getConstants
(JNIEnv *env, jclass clazz) {
	jint is[] = {
		offsetof(lgLcdOpenByTypeContext, device),	// cDeviceOffset	0
		offsetof(lgLcdConnectContextEx, connection),// cConnectionOffset1
		offsetof(lgLcdBitmapQVGAx32, pixels),		// cPixelsOffset	2
		LGLCD_BMP_FORMAT_160x43x1,					
		LGLCD_BMP_FORMAT_QVGAx32,					
		LGLCD_NOTIFICATION_DEVICE_ARRIVAL,
		LGLCD_NOTIFICATION_DEVICE_REMOVAL,
		LGLCD_NOTIFICATION_APPLET_ENABLED,
		LGLCD_NOTIFICATION_APPLET_DISABLED,
		LGLCD_NOTIFICATION_CLOSE_CONNECTION,
		LGLCD_APPLET_CAP_BASIC,
		LGLCD_APPLET_CAP_BW,
		LGLCD_APPLET_CAP_QVGA,
		LGLCD_DEVICE_BW,
		LGLCD_DEVICE_QVGA,
		RPC_S_SERVER_UNAVAILABLE,
		ERROR_OLD_WIN_VERSION,
		ERROR_NO_SYSTEM_RESOURCES,
		ERROR_ALREADY_INITIALIZED,
		ERROR_SUCCESS,
		ERROR_SERVICE_NOT_ACTIVE,
		ERROR_INVALID_PARAMETER,
		ERROR_FILE_NOT_FOUND,
		ERROR_ALREADY_EXISTS,
		RPC_X_WRONG_PIPE_VERSION,
		ERROR_DEVICE_NOT_CONNECTED,
		LGLCD_PRIORITY_IDLE_NO_SHOW,
		LGLCD_PRIORITY_BACKGROUND,
		LGLCD_PRIORITY_NORMAL,
		LGLCD_PRIORITY_ALERT,
        LGLCD_SYNC_UPDATE(0),
		LGLCD_ASYNC_UPDATE(0),
        LGLCD_SYNC_COMPLETE_WITHIN_FRAME(0),
		LGLCD_LCD_FOREGROUND_APP_NO,
		LGLCD_LCD_FOREGROUND_APP_YES,
		ERROR_LOCK_FAILED,
		LGLCDBUTTON_LEFT,
		LGLCDBUTTON_RIGHT,
		LGLCDBUTTON_OK,
		LGLCDBUTTON_CANCEL,
		LGLCDBUTTON_UP,
		LGLCDBUTTON_DOWN,
		LGLCDBUTTON_MENU,
		LGLCDBUTTON_BUTTON0,
		LGLCDBUTTON_BUTTON1,
		LGLCDBUTTON_BUTTON2,
		LGLCDBUTTON_BUTTON3,
		LGLCDBUTTON_BUTTON4,
		LGLCDBUTTON_BUTTON5,
		LGLCDBUTTON_BUTTON6,
		LGLCDBUTTON_BUTTON7
	};

	jsize sizeIs = sizeof(is) / sizeof(jint);
	jintArray ret = env->NewIntArray(sizeIs);
	env->SetIntArrayRegion(ret, 0, sizeIs, is);
	return ret;
}

#ifdef __cplusplus
}
#endif

